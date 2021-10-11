package de.eyeled.fue.basyx.android.lib.service

import android.app.Service
import android.util.Log
import de.eyeled.fue.basyx.android.lib.aas.*
import de.eyeled.fue.basyx.android.lib.helper.Util
import de.eyeled.fue.basyx.android.lib.repository.data.BaSyxError
import de.eyeled.fue.basyx.android.lib.repository.interfaces.AasRepository
import de.eyeled.fue.basyx.android.lib.repository.listener.RepositoryListener
import de.eyeled.fue.basyx.android.lib.repository.operations.InvokeOperation
import de.eyeled.fue.basyx.android.lib.service.components.RegistryComponent
import de.eyeled.fue.basyx.android.lib.service.data.BaSyxAasTypes
import de.eyeled.fue.basyx.android.lib.service.data.BaSyxDataCache
import de.eyeled.fue.basyx.android.lib.service.data.BaSyxDataFactory
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier
import org.eclipse.basyx.vab.exception.provider.ProviderException
import java.util.*
import java.util.concurrent.*
import kotlin.collections.HashMap

abstract class BaSyxService : Service(), AasRepository {

    protected val mRegistryComponents = CopyOnWriteArrayList<RegistryComponent>()
    protected var mBaSyxDataFactory = BaSyxDataFactory()

    protected val mRepositoryListener = CopyOnWriteArrayList<RepositoryListener>()
    private val mAasEndpointMap : HashMap<String, String> = HashMap()
    private val mAasRegistryMap : HashMap<String, String> = HashMap()
    private var mainRegistry: RegistryComponent? = null

    enum class Status {
        DISCONNECTED, CONNECTED, CONNECTING
    }

    protected var mStatus = Status.DISCONNECTED

    companion object {
        private const val TAG = "BaSyxService"
    }

    @Synchronized
    protected fun addRegistryComponent(component: RegistryComponent){
        mRegistryComponents.add(component)
        if(component.aasType == BaSyxAasTypes.MAIN){
            mainRegistry = component
        }
    }

    @Synchronized
    public fun addRegistryElement(element: RegistryElement){
        if(element.endpoint != null && !hasRegistryEndpoint(element.endpoint)) {
            addRegistryComponent(RegistryComponent(element.getType(), element.endpoint))
        }
    }

    /**
     * Get the registry server object for the given AAS Type.
     */
    override fun getAasRegistryByType(aasType: String): RegistryComponent?{
        for(component in mRegistryComponents){
            if(component.aasType == aasType){
                return component
            }
        }

        // return main registry if available
        return mainRegistry
    }

    override fun getAasRegistryByEndpoint(endpoint: String): RegistryComponent?{
        for(component in mRegistryComponents){
            if(component.registryServiceAddress == endpoint){
                return component
            }
        }

        // return main registry if available
        return mainRegistry
    }

    override fun getAasRegistryByAas(aasId: String): RegistryComponent?{
        val endpoint = mAasRegistryMap[aasId]
        if(endpoint != null) {
            return getAasRegistryByEndpoint(endpoint)
        }

        // return main registry if available
        return mainRegistry
    }

    private fun hasRegistryEndpoint(endpoint: String?): Boolean{
        if(endpoint != null) {
            return getAasRegistryByEndpoint(endpoint) != null
        }

        return false
    }

    @Synchronized
    override fun addRepositoryListener(listener: RepositoryListener){
        mRepositoryListener.add(listener)
    }

    @Synchronized
    override fun removeRepositoryListener(listener: RepositoryListener){
        mRepositoryListener.remove(listener)
    }

    @Synchronized
    fun informListenerOnNewError(error: BaSyxError){
        for(listener in mRepositoryListener){
            listener.onError(error)
        }
    }

    /**
     * Create ConnectedAssetAdministrationShell using the given aas registry and the aas
     * identifier.
     *
     * Call is a network call and must be used on a separate thread!
     */
    @Throws(Exception::class)
    private fun getConnectedAAS(registryComponent: RegistryComponent?, aasURN: IIdentifier): ConnectedAssetAdministrationShell? {
        return registryComponent?.manager?.retrieveAAS(aasURN)
    }

    /**
     * Load and get the Android aas list using the given aas descriptor list. If activated
     * the sub models will also be loaded.
     */
    override fun loadAndGetAasList(registryComponent: RegistryComponent,
                                   loadSubModels: Boolean,
                                   useCached: Boolean): List<AndroidAssetAdministrationShell>? {
        try{
            // load all available aas descriptor information
            val aasDescriptors : List<AASDescriptor> = registryComponent.registryService.lookupAll()

            // validate
            if(!aasDescriptors.isNullOrEmpty()) {
                val aasList = ArrayList<AndroidAssetAdministrationShell>()
                val executor: ExecutorService = Executors.newFixedThreadPool(4)
                val taskList: ArrayList<FutureTask<AndroidAssetAdministrationShell>> = ArrayList()

                for (i in aasDescriptors.indices) {
                    try {
                        val descriptor = aasDescriptors[i]
                        val id = descriptor.identifier.id
                        val idType = mBaSyxDataFactory.getAASType(id)

                        mAasEndpointMap[id] = descriptor.firstEndpoint
                        mAasRegistryMap[id] = registryComponent.registryServiceAddress

                        // try to load the cached aas
                        val cachedAAas = if(useCached) getCachedAAas(idType, id) else null
                        if(cachedAAas != null){
                            aasList.add(cachedAAas)
                        }
                        else {
                            // load aas data
                            val aAasLoader = loadAndGetAasAsync(id, loadSubModels)

                            if(aAasLoader != null){
                                taskList.add(aAasLoader)
                                executor.execute(aAasLoader)
                            }
                        }

                        // TODO: error handling for aas if not available
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(TAG, "error: " + e.localizedMessage)
                    }
                }

                // Wait until all results are available and combine them at the same time
                for (task in taskList) {
                    val aAas = task.get()
                    if (aAas != null) {
                        val id = aAas.id
                        aAas.address = mAasEndpointMap[id]
                        if (!BaSyxDataFactory.listContainsAas(aasList, id)) {
                            aasList.add(aAas)
                        }
                    }
                }
                executor.shutdown()

                registryComponent.lastUpdated = System.currentTimeMillis()


                Log.d("Basyx", "AAS data loaded from "+registryComponent.registryServiceAddress)

                return aasList
            }
        }catch (e: java.lang.Exception){
            e.printStackTrace()
            Log.e("BaSyxService", "retrieveAasList error");
        }


        return null
    }

    override fun getAasList(aasType: String): List<AndroidAssetAdministrationShell>? {
        return BaSyxDataCache.i().getAasList(aasType)
    }

    private fun loadAndGetAasAsync(id: String, loadSubModels: Boolean): FutureTask<AndroidAssetAdministrationShell>? {
        Log.d(TAG, "loadAas $id")
        try {
            val task = FutureTask<AndroidAssetAdministrationShell>(object : Callable<AndroidAssetAdministrationShell?> {
                override fun call(): AndroidAssetAdministrationShell? {
                    return loadAndGetAas(id, loadSubModels)
                }
            })
            return task
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "error loadAas: " + id + " " + e.message)
        }

        return null
    }


    /**
     * Return the Android AAS for the given id. If available the cached AAS will be used.
     * Submodels will be automatically loaded.
     */
    override fun getAas(id: String): AndroidAssetAdministrationShell? {
        var aaas = getCachedAAas(id)

        if(aaas == null){
            aaas = loadAndGetAas(id)
        }

        return aaas
    }

    /**
     * Load and return the Android AAS for the given id.
     * Submodels will be automatically loaded.
     * The aas type will be validated automatically
     */
    override fun loadAndGetAas(id: String): AndroidAssetAdministrationShell? {
        return loadAndGetAas(id, true)
    }

    /**
     * Load and return the Android AAS for the given id and type.
     */
    override fun loadAndGetAas(id: String, loadSubModels: Boolean): AndroidAssetAdministrationShell? {
        try {
            val registryAddress = mAasRegistryMap[id]

            if(registryAddress != null) {
                val registry = getAasRegistryByEndpoint(registryAddress)
                // get the connected basyx aas using the registry for the given type
                val bAas = getConnectedAAS(registry, ModelUrn(id))

                if (bAas != null) {
                    // get the aas type (user, device, unknown)
                    val aAas = mBaSyxDataFactory.loadAaasData(bAas, loadSubModels)
                    aAas?.address = mAasEndpointMap[aAas?.id]

                    cacheAAas(mBaSyxDataFactory.getAASType(id), aAas)

                    return aAas
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "error " + e.message)
        }

        return null
    }

    /**
     * Load the Android SubModel for the give AAS and the given sub model idShort.
     */
    override fun loadAndGetSubModel(aasId: String, smIdShort: String): AndroidSubModel? {
        try {
            val aAas = getCachedAAas(aasId)
            if(aAas != null) {
                loadSubModelData(aAas)
                return aAas.getSubModel(smIdShort)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "error loadSubModel: " + e.message)
        }

        return null
    }

    /**
     * Retrieve the sub models for the given Android AAS via BaSys Network
     */
    override fun loadSubModelData(aAas: AndroidAssetAdministrationShell){
        mBaSyxDataFactory.loadAaasData(aAas, true)
    }

    /**
     * Retrieve the sub model data for the given Android AAS and sub model id via BaSys Network
     */
    override fun loadSubModelData(aAas: AndroidAssetAdministrationShell, smIdShort: String){
        mBaSyxDataFactory.loadSubModelData(aAas, smIdShort)
    }

    /**
     * Load the data of the given dataElement. Value will be cast to String
     */
    override fun loadDataElementValue(dataElement: AndroidDataElement) {
        dataElement.loadDataElementValue()
    }

    /**
     * Set the value for the given data element. This will update the basyx sub model data element
     * via REST
     */
    override fun setDataElement(dataElement: AndroidDataElement): Boolean {
        return mBaSyxDataFactory.setDataElement(dataElement)
    }

    /**
     * Invoke the given operation using the given parameter
     */
    @Throws(Exception::class)
    override operator fun invoke(invokeOperation: InvokeOperation, vararg params: Any?): Any?{
        return invokeOperation.invoke(this, *params)
    }

    /**
     * Invoke the given AndroidOperation using the given String parameter
     */
    override operator fun invoke(androidOperation: AndroidOperation, param: String?): Any?{
        try {
            val response = androidOperation.iOperation?.invoke(param)
            androidOperation.opResponse = response
            return response
        }
        catch (providerEx: ProviderException){}
        catch (e: java.lang.Exception){}

        return null
    }

    /**
     * Invoke the given AndroidOperation using the given String parameter
     */
    override fun invoke(androidOperation: AndroidOperation): Any?{
        try {
            val params = Util.getOperationDataObjects(androidOperation)
            val response = androidOperation.iOperation?.invoke(*params)
            androidOperation.opResponse = response
            return response
        }
        catch (providerEx: ProviderException){
            Log.e("BaSyxService", "invokeMultipleError ProviderException " + providerEx.message)}
        catch (e: java.lang.Exception){
            Log.e("BaSyxService", "invokeMultipleError " + e.message)
        }

        return null
    }

    /**
     * HELPER
     */

    override fun getCachedAAas(id: String): AndroidAssetAdministrationShell?{
        return BaSyxDataCache.i().getAas(id)
    }

    override fun getCachedAAas(type: String, id: String): AndroidAssetAdministrationShell?{
        return BaSyxDataCache.i().getAas(type, id)
    }

    private fun cacheAAas(type: String, aAas: AndroidAssetAdministrationShell?){
        if(aAas != null) {
            BaSyxDataCache.i().addAaas(aAas, type)
        }
    }

    override fun getSubModel(aasId: String, smIdShort: String): AndroidSubModel? {
        val aAas = BaSyxDataCache.i().getAas(aasId)
        return aAas?.getSubModel(smIdShort)
    }

    override fun getStatus(): Status {
        return mStatus
    }

    fun updateStatus(status: Status){
        Log.e(TAG, "updateStatus: $status")
        mStatus = status
        for(listener in mRepositoryListener){
            listener.onStatusUpdated(mStatus)
        }
    }

    override fun clearBasysCache() {
        Log.d(TAG, "clearBasysCache")
        BaSyxDataCache.i().clear()
    }

    override fun clearRegistryEndpoints() {
        Log.d(TAG, "clearRegistryEndpoints")
        mRegistryComponents.clear()
    }
}
