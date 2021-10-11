package de.eyeled.fue.basyx.android.bdevws.lib.service

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.google.gson.JsonObject
import de.eyeled.fue.basyx.android.bdevws.lib.aas.DeviceAndroidAAS
import de.eyeled.fue.basyx.android.bdevws.lib.aas.DeviceSetupElement
import de.eyeled.fue.basyx.android.bdevws.lib.aas.UserAndroidAAS
import de.eyeled.fue.basyx.android.bdevws.lib.repository.error.BdeBaSyxError
import de.eyeled.fue.basyx.android.bdevws.lib.repository.interfaces.BdeAasRepository
import de.eyeled.fue.basyx.android.bdevws.lib.repository.listener.BdeRepositoryListener
import de.eyeled.fue.basyx.android.bdevws.lib.repository.operations.UserLoginOperation
import de.eyeled.fue.basyx.android.bdevws.lib.repository.services.BdeBackendCommunication
import de.eyeled.fue.basyx.android.bdevws.lib.repository.services.CommunicationService
import de.eyeled.fue.basyx.android.bdevws.lib.service.data.BdeBaSyxDataFactory
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation
import de.eyeled.fue.basyx.android.lib.communication.http.CustomURLStreamHandlerFactory
import de.eyeled.fue.basyx.android.lib.communication.http.HttpResponseListener
import de.eyeled.fue.basyx.android.lib.helper.PropertyFile
import de.eyeled.fue.basyx.android.lib.helper.Util
import de.eyeled.fue.basyx.android.lib.repository.listener.DataListener
import de.eyeled.fue.basyx.android.lib.service.BaSyxService
import de.eyeled.fue.basyx.android.lib.service.components.RegistryComponent
import de.eyeled.fue.basyx.lib.aas.user.UserViewSubModel
import de.eyeled.fue.basyx.lib.connection.ClientFactory
import de.eyeled.fue.basyx.lib.connection.ConnectionClient
import de.eyeled.fue.basyx.lib.connection.ConnectionProperty
import de.eyeled.fue.basyx.lib.connection.data.ConnectionType
import de.eyeled.fue.basyx.lib.connection.data.DataType
import de.eyeled.fue.basyx.lib.connection.interfaces.ClientConnectionListener
import de.eyeled.fue.basyx.lib.context.data.ServiceResponse
import org.eclipse.basyx.vab.exception.provider.ProviderException
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.collections.HashMap

@Suppress("SameParameterValue")
open class BdeBaSyxService: BaSyxService(), BdeAasRepository, ClientConnectionListener, HttpResponseListener {
    private val mBinder: IBinder = ServiceBinder()

    // the device aas
    private var mDeviceAasId : String? = null
    private var mDeviceAas: DeviceAndroidAAS? = null
    private var mBdeHostAddress : String? = null
    private var mKafkaHostAddress : String? = null

    // the message broker service
    private val registerMessageBroker: Boolean = false
    private var mMessageBroker : ConnectionClient? = null

    private var mClientConnectionListener :
            HashMap<BdeRepositoryListener, CopyOnWriteArrayList<ClientConnectionListener>> = HashMap()

    // the connection endpoint for device registration
    private var mDeviceSetupElement: DeviceSetupElement? = null
        set(element){
            if(element != null && !element.isSame(field)){
                field = element
            }
        }

    inner class ServiceBinder : Binder() {
        val service: BdeBaSyxService
            get() = this@BdeBaSyxService
    }

    companion object {
        private const val TAG = "BdeBaSyxService"
        private const val PREF_NAME = "basyx_prefs"
        const val PREF_APP_ID = "basyx_app_id"
        const val KAFKA_HOST = "kafka_host"
        const val BDE_HOST = "bde_host"
        fun getSharedPreferences(baSyxService: BaSyxService): SharedPreferences {
            return baSyxService.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    override fun onCreate() {
        setCustomUrlStreamHandlerFactory()
        super.onCreate()
        mBaSyxDataFactory = BdeBaSyxDataFactory()
    }

    private fun setCustomUrlStreamHandlerFactory(){
        try {
            // we have to set a custom url stream handler
            // otherwise connections to AAS may have issues
            URL.setURLStreamHandlerFactory(CustomURLStreamHandlerFactory())
        } catch (e: Error) { // we have to use try/catch in cases the factory is already set
            Log.e(TAG, "CustomURLStreamHandlerFactory already set!")
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        mMessageBroker?.stop()
    }

    private fun registerDevice(){
        if(mDeviceSetupElement != null) {
            // clear the basys AAS cache
            clearBasysCache()
            // clear previous set registry endpoints
            clearRegistryEndpoints()

            mExtraData = null

            updateStatus(Status.CONNECTING)
            Log.d("BasyxService", "registerDevice Connecting")
            BdeBackendCommunication.i()
                    .registerDevice(
                            this,
                            createCreateDeviceHTTPResponseListener(),
                            mDeviceSetupElement!!.endpoint, generateDeviceInformation())
        }
    }

    private fun createCreateDeviceHTTPResponseListener() : HttpResponseListener{
        return HttpResponseListener { data, type, _, _ ->
            if(type == DataType.JSON){
                val parseJsonRunnable = Runnable {
                    var connectionStatus = Status.DISCONNECTED
                    try {
                        Log.d("BasyxService", "onHttpResponse $data")
                        val jsonData = data as JSONObject
                        val response = ServiceResponse.create(jsonData.toString())

                        if("deviceSetup".compareTo(response.service) == 0) {
                            if(response.isSuccess) {
                                try {
                                    val extras = response.getDataJsonObject("extras")
                                    mExtraData = extras?.toString()
                                    val aasList = response.getDataJsonArray("aas")
                                    for (x in 0 until aasList.size()) {
                                        val aas = aasList.get(x) as JsonObject
                                        val aasId = aas.getAsJsonPrimitive("id").asString
                                        val aasShort = aas.getAsJsonPrimitive("idShort").asString
                                        if (getUniqueAppId().contentEquals(aasShort)) {
                                            mDeviceAasId = aasId
                                        }
                                    }


                                    if(extras.has("bdeHost")) {
                                        mBdeHostAddress = extras.getAsJsonPrimitive("bdeHost").asString
                                        setSharedPreference(BDE_HOST, mBdeHostAddress!!)
                                        informListenerOnBdeAddress(mBdeHostAddress!!)
                                        Log.d("BasyxService", "onHttpResponse bdeHost $mBdeHostAddress")
                                    }

                                    if(extras.has("kafkaHost")) {
                                        mKafkaHostAddress = extras.getAsJsonPrimitive("kafkaHost").asString
                                        setSharedPreference(KAFKA_HOST, mKafkaHostAddress!!)
                                        informListenerOnKafkaAddress(mKafkaHostAddress!!)
                                        Log.d("BasyxService", "onHttpResponse kafkaHost $mKafkaHostAddress")
                                    }

                                    val registryEndpoints = extras.getAsJsonObject("registryEndpoints")
                                    for (key in registryEndpoints.keySet()) {
                                        addRegistryComponent(RegistryComponent(key, registryEndpoints.getAsJsonPrimitive(key).asString))
                                    }

                                    if (mDeviceAasId.isNullOrBlank()) {
                                        val bdeError = BdeBaSyxError(BdeBaSyxError.Type.CONNECTION_DEVICE_SETUP)
                                        informListenerOnNewError(bdeError)
                                    } else {
                                        val aas = loadAndGetAas(mDeviceAasId!!, true)
                                        if (aas != null) {
                                            mDeviceAas = aas as DeviceAndroidAAS
                                            registerMessageBroker()
                                        } else {
                                            informListenerOnNewError(BdeBaSyxError(BdeBaSyxError.Type.CONNECTION_NO_DEVICE_AAS_SERVER))
                                        }

                                        loadAasLists(true)
                                    }

                                    connectionStatus = Status.CONNECTED

                                } catch (e: Exception){
                                    e.printStackTrace()
                                    Log.e("BdeBasyxService", "onNewResponse error " + e.message)
                                    val bdeError = BdeBaSyxError(BdeBaSyxError.Type.CONNECTION_DEVICE_SETUP)
                                    informListenerOnNewError(bdeError)
                                }
                            }
                        }
                    } catch (e: Exception){
                        e.printStackTrace()
                        Log.e("BdeBasyxService", "onNewResponse error " + e.message)
                    }

                    updateStatus(connectionStatus)
                }

                Thread(parseJsonRunnable).start()
            } else if(type == DataType.ERROR){
                val bdeError = BdeBaSyxError(BdeBaSyxError.Type.CONNECTION_DEVICE_SETUP)
                informListenerOnNewError(bdeError)
                updateStatus(Status.DISCONNECTED)
            }
        }
    }

    override fun onLoginOperation(username: String, password: String, dataListener: DataListener){
        val loginRunnable = Runnable {
            Log.d(TAG, "onLoginOperation $username")
            // create a login operation
            val operation = UserLoginOperation()
            // invoke the operation using the user name and password
            val userAas = invoke(operation, username, password)

            // return service response
            val serviceResponse = ServiceResponse("login")
            if(userAas != null && userAas is UserAndroidAAS){
                serviceResponse.isSuccess = true
                serviceResponse.addData("aas", userAas.id)
            }
            else {
                serviceResponse.isSuccess = false
            }

            dataListener.onNewData(JSONObject(serviceResponse.toString()))
        }
        Thread(loginRunnable).start()
    }

    /**
     * Handle onNewResponse data (typically HTTP Requests)
     */
    override fun onHttpResponse(data: Any?, type: DataType?, id: String, host: String) {

    }


    /**
     * Load the aas lists using the registry server
     */
    private fun loadAasLists(loadSubmodels: Boolean) {
        val thread = Thread {
            for (registry in mRegistryComponents) {
                loadAndGetAasList(registry, loadSubmodels, false)
            }
            Log.d("BasyxService", "loadAasLists done $loadSubmodels ")
        }
        thread.start()
    }

    private fun registerMessageBroker(){
        if(registerMessageBroker && mDeviceAas != null){
            try {
                val response = mDeviceAas?.initMessageBrokerConnection(getUniqueAppId())
                val property: ConnectionProperty? = ConnectionProperty.createConnectionProperty(response)

                if (property != null &&
                        property.type?.equals(ConnectionType.MESSAGE_BROKER) == true &&
                        property.id.isNotBlank()) {

                    if (mMessageBroker != null) {
                        mMessageBroker?.stop()
                    }
                    mMessageBroker = ClientFactory.createConnection(property, this)
                }
            }
            catch (e: java.lang.Exception){
                informListenerOnNewError(BdeBaSyxError(BdeBaSyxError.Type.CONNECTION))
            }
        }
    }

    /**
     * Invoke the given AndroidOperation using the given String parameter
     */
    override operator fun invoke(androidOperation: AndroidOperation, listener: BdeRepositoryListener): Any?{
        try {
            val data = super.invoke(androidOperation)
            analyseOperationResponse(data, listener, androidOperation)
            return data
        }
        catch (providerEx: ProviderException){}
        catch (e: java.lang.Exception){}

        return null
    }

    private fun analyseOperationResponse(result: Any?, listener: BdeRepositoryListener, androidOperation: AndroidOperation){
        if(result is String && result.contains(ConnectionProperty.ID)) {
            createPropertyConnection(result, listener, androidOperation)
        }
    }

    private fun createPropertyConnection(json: String, repositoryListener: BdeRepositoryListener, androidOperation: AndroidOperation){
        val property: ConnectionProperty? = ConnectionProperty.createConnectionProperty(json)

        if(property != null) {
            val clientConnectionListener: ClientConnectionListener = object : ClientConnectionListener {
                override fun onNewData(o: Any, connectionClient: ConnectionClient) {
                    repositoryListener.onNewOperationData(property, androidOperation, o)}
                override fun onConnectionClosed(connectionClient: ConnectionClient) {
                    repositoryListener.onClientConnection(property, androidOperation, false)}
                override fun onConnectionStarted(connectionClient: ConnectionClient) {
                    repositoryListener.onClientConnection(property, androidOperation, false)}
            }

            addRepositoryConnectionPropertyListener(repositoryListener, clientConnectionListener)

            CommunicationService.i().startConnection(property, clientConnectionListener)
        }
    }

    private fun addRepositoryConnectionPropertyListener(repositoryListener: BdeRepositoryListener,
                                                        clientConnectionListener: ClientConnectionListener){
        var list: CopyOnWriteArrayList<ClientConnectionListener>? = mClientConnectionListener[repositoryListener]
        if (list == null) {
            list = CopyOnWriteArrayList()
            mClientConnectionListener[repositoryListener] = list
        }
        list.add(clientConnectionListener)
    }


    override fun removeStreamConnection(listener: BdeRepositoryListener) {
        val clientConnectionListener = mClientConnectionListener[listener]
        if(clientConnectionListener != null && clientConnectionListener.size > 0){
            for (clientListener in clientConnectionListener) {
                CommunicationService.i().removeConnectionListener(clientListener)
            }

            mClientConnectionListener.remove(listener)
        }
    }

    override fun onConnectionStarted(p0: ConnectionClient?) {}

    override fun onNewData(p0: Any?, p2: ConnectionClient?) {
        Log.d(TAG, "onNewData $p0")
    }

    override fun onConnectionClosed(p0: ConnectionClient?) {}

    private fun getSharedPreference(key: String): String?{
        return getSharedPreferences(this).getString(key, null)
    }

    private fun setSharedPreference(key: String, value: String){
        getSharedPreferences(this).edit().putString(key, value).apply()
    }

    override fun getUniqueAppId(): String {
        val appId = getSharedPreference(PREF_APP_ID)
        if(appId == null || appId.contains("-")){
            setSharedPreference(PREF_APP_ID, createUniqueAppId())
        }

        return getSharedPreference(PREF_APP_ID)!!
    }

    override fun getKafkaAddress(): String? {
        return mKafkaHostAddress
    }

    override fun getDeviceAASId(): String? {
        return mDeviceAasId
    }

    override fun getBdeAddress(): String? {
        return mBdeHostAddress
    }

    private fun createUniqueAppId(): String{
        return "Device_"+UUID.randomUUID().toString().replace("-", "_")
    }


    @Synchronized
    fun informListenerOnKafkaAddress(address: String){
        for(listener in mRepositoryListener){
            if(listener is BdeRepositoryListener) {
                listener.onKafkaAddress(address)
            }
        }
    }

    @Synchronized
    fun informListenerOnBdeAddress(address: String){
        for(listener in mRepositoryListener){
            if(listener is BdeRepositoryListener) {
                listener.onBdeAddress(address)
            }
        }
    }


    /**
     * Helper method to generate a json object containing device information to be sent
     * during device registration.
     */
    private fun generateDeviceInformation():JSONObject{
        val pm: PackageManager = this.packageManager
        val appId = getUniqueAppId()
        val data = JSONObject()
        val deviceList = JSONArray()
        data.put("Geraete", deviceList)
        val deviceJson = JSONObject()
        deviceList.put(deviceJson)
        val stammDaten = JSONObject()
        val properties = JSONArray()
        val description = JSONObject()
        stammDaten.put("id", appId)
        stammDaten.put("ip", Util.getIPAddress(true))
        stammDaten.put("mac", Util.getMACAddress(null))
        stammDaten.put("model", Build.MODEL)
        stammDaten.put("product", Build.PRODUCT)
        stammDaten.put("manufacturer", Build.MANUFACTURER)
        if(pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY))
            properties.put("Telefonie")
        if(pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH))
            properties.put("Bluetooth")
        if(pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
            properties.put("Bluetooth_LE")
        if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
            properties.put("Camera")
        if(pm.hasSystemFeature(PackageManager.FEATURE_NFC))
            properties.put("NFC")
        if(pm.hasSystemFeature(PackageManager.FEATURE_WIFI))
            properties.put("Wifi")
        stammDaten.put("Properties", properties)
        description.put("DE", "Android Gerät [$appId]")
        description.put("EN", "Android Device [$appId]")
        stammDaten.put("Beschreibung", description)
        deviceJson.put("Stammdaten", stammDaten)
        return data
    }



    private var mExtraData : String? = null

    override fun registerDevice(deviceServer: String?, force: Boolean): Future<String> {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        return executor.submit<String> {
            Log.d("BasyxService", "registerDevice $deviceServer")
            if(readDeviceSetupInformation(deviceServer) || force){
                registerDevice()
            }

            while (mStatus == Status.CONNECTING){
                Thread.sleep(50)
            }
            Log.d("BasyxService", "registerDevice $mExtraData")

            mExtraData
        }
    }

    override fun loadDefaultView(userId: String): Future<String> {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        return executor.submit<String> {
            val userAAS = getAas(userId)
            if(userAAS != null) {
                // reload view data
                loadSubModelData(userAAS, UserViewSubModel.SUBMODEL_VIEW)
            }
            var submit = ""
            if(userAAS is UserAndroidAAS && userAAS.defaultViewData != null){
                submit = userAAS.defaultViewData
            }

            submit
        }
    }

    override fun loadView(userId: String, viewId: String?): Future<String> {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        return executor.submit<String> {
            val userAAS = getAas(userId)
            if(userAAS != null) {
                // reload view data
                loadSubModelData(userAAS, UserViewSubModel.SUBMODEL_VIEW)
            }
            var submit = ""
            if(userAAS is UserAndroidAAS){
                submit = userAAS.getViewData(viewId)
            }

            submit
        }
    }

    override fun writeDefaultView(userId: String, data: String): Future<Boolean> {
        return writeView(userId, null, data)
    }

    override fun writeView(userId: String, viewId: String?, data: String): Future<Boolean> {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        return executor.submit<Boolean> {
            Log.d("BasyxService", "writeDefaultView $userId")
            val userAAS = loadAndGetAas(userId, true)
            var submit = false
            if(userAAS is UserAndroidAAS){
                submit = userAAS.writeViewData(viewId, data)
            }

            submit
        }
    }

    /**
     * Read the device setup server information. This info is either set in:
     * - method parameter
     * - asset file (config)
     */
    private fun readDeviceSetupInformation(server: String?) : Boolean{
        Log.d("BasyxService", "readDeviceSetupInformation $server")
        var deviceServer = server

        if(deviceServer.isNullOrEmpty()){
            deviceServer = PropertyFile.getProperty("deviceServer", this)
        }

        if(deviceServer != null){
            val element = DeviceSetupElement()
            element.endpoint = deviceServer
            element.main = true
            if(!element.isSame(mDeviceSetupElement)) {
                mDeviceSetupElement = element
                return true
            }
        }

        return false
    }
}