package de.eyeled.fue.basyx.android.aasapp

import android.content.*
import android.os.IBinder
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import de.eyeled.fue.basyx.android.bdevws.lib.viewmodel.AasViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import de.eyeled.fue.basyx.android.lib.helper.PropertyFile
import de.eyeled.fue.basyx.android.lib.repository.data.BaSyxError
import de.eyeled.fue.basyx.android.lib.aas.RegistryElement
import com.google.android.material.snackbar.Snackbar
import de.eyeled.fue.basyx.android.bdevws.lib.repository.error.BdeBaSyxError
import de.eyeled.fue.basyx.android.bdevws.lib.service.BdeBaSyxService

abstract class DefaultActivity : AppCompatActivity() {
    // Static
    companion object {
        private val TAG = "AasRegistryActivity"
        const val PROPERTY_REGISTRY_SERVER = "registryServer";
    }

    // the basys background service
    protected var mBaSyxService: BdeBaSyxService? = null
    protected var mServiceConnection: ServiceConnection? = null
    // the view model
    protected var mAasViewModel: AasViewModel? = null

    // device setup server information
    var mRegistryServerList: ArrayList<RegistryElement> = ArrayList()

    protected var mFab: FloatingActionButton? = null

    override fun onResume() {
        super.onResume()
        // init the service
        initBaSyxServiceConnection()
        // read the device setup server information
        readBasysRegistryInformation()
        // if the basys service is already loaded update the service config parameter
        if (mBaSyxService != null && mAasViewModel != null) {
            updateBaSyxServiceConfig()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mAasViewModel?.removeObservers(this)
        unbindBaSyxService()
    }

    /**
     * Init the basyx background service by starting the service
     */
    protected fun initBaSyxServiceConnection() {
        if (lifecycle.currentState.isAtLeast(STARTED)) {
            val intent = Intent(this, BdeBaSyxService::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (lifecycle.currentState.isAtLeast(RESUMED)) {
                startService(intent)
            }
        }

        if (mServiceConnection == null) {
            mServiceConnection = object : ServiceConnection {
                override fun onServiceConnected(className: ComponentName, binder: IBinder) {
                    val b = binder as BdeBaSyxService.ServiceBinder
                    mBaSyxService = b.service

                    if (mBaSyxService != null) {
                        initViewModel()
                        if(mRegistryServerList.isEmpty()){
                            readBasysRegistryInformation()
                        }
                        updateBaSyxServiceConfig()
                        onBaSyxServiceLoaded()
                    }
                }

                override fun onServiceDisconnected(className: ComponentName) {
                    mBaSyxService = null
                }
            }
        }

        val intent = Intent(this, BdeBaSyxService::class.java)
        bindService(intent, mServiceConnection!!, Context.BIND_AUTO_CREATE)
    }

    /**
     * Read the device setup server information. This info is either set in:
     * - shared preferences (user)
     * - asset file (config)
     */
    private fun readBasysRegistryInformation(){
        val list = readSharedPrefList(RegistryActivity.BASYS_REGISTRY_PREF_TAG)
        mRegistryServerList.clear()
        if(list != null){
            for(prefElement in list){
                mRegistryServerList.add(RegistryActivity.getBasysRegistryElementData(prefElement))
            }
        }

        if(mRegistryServerList.isEmpty()){
            val registryEndpoint = PropertyFile.getProperty(PROPERTY_REGISTRY_SERVER,this)
            if(registryEndpoint != null){
                val element = RegistryElement(registryEndpoint, true)
                mRegistryServerList.add(element)
            }
        }
    }

    /**
     * Update the basyx service config parameter:
     * - device setup server
     */
    fun updateBaSyxServiceConfig(){
        if(mBaSyxService != null && mRegistryServerList.isNotEmpty()){
            for(registryElement in mRegistryServerList){
                mBaSyxService?.addRegistryElement(registryElement);
            }
        }
    }

    /**
     * Helper method to unbid the basyx service
     */
    protected fun unbindBaSyxService() {
        if (mServiceConnection != null && mBaSyxService != null) {
            try {
                unbindService(mServiceConnection!!)
            } catch (e: Exception) {
                Log.e(TAG, "unbindBaSyxService error: ", e)
            }

        }
    }

    /**
     *  VIEW MODEL
     */

    /**
     * Init the view model provider. Should be called when the basyx service is loaded.
     */
    protected open fun initViewModel() {
        mAasViewModel = ViewModelProvider(this).get(getAasViewModelClass())
        mAasViewModel?.setAasRepository(mBaSyxService)
        mAasViewModel?.setBdeAasRepository(mBaSyxService)
        mAasViewModel?.mErrorLiveData?.observe(this, Observer{
            bdeError -> onError(bdeError)
        })
    }

    /**
     * Abstract method to change the view model provider
     */
    abstract fun getAasViewModelClass() : Class<out AasViewModel>

    protected abstract fun onBaSyxServiceLoaded()

    protected fun onError(bdeError: BaSyxError){
        checkErrorType(bdeError)
        if(bdeError.mData != null || bdeError.mResourceId != null) {
            var text = bdeError.mData

            if (bdeError.mResourceId != null) {
                text = getString(bdeError.mResourceId!!)
            }

            Snackbar.make(
                    findViewById(R.id.coordinatorLayout),
                    text!!,
                    Snackbar.LENGTH_LONG
            ).show()
        }

    }

    protected fun checkErrorType(error: BaSyxError?): Int{
        if(error != null && error is BdeBaSyxError){
            val type = error.type
            when (type) {
                BdeBaSyxError.Type.VIEW -> return R.string.error_view
                BdeBaSyxError.Type.CONNECTION -> return R.string.error_connection
                BdeBaSyxError.Type.CONNECTION_DEVICE_SETUP -> return R.string.error_connection_device_setup
            }
        }
        return R.string.error
    }

    fun showSnackbar(text : String){
        if(text != null) {
            Snackbar.make(
                    findViewById(R.id.coordinatorLayout),
                    text,
                    Snackbar.LENGTH_LONG
            ).show()
        }
    }

    /**
     * HELPER Shared Preferences
     */

    protected fun writeSharedPref(key: String, value: String) {
        val sharedPref = getSharedPreferences()
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    protected fun removeSharedPref(key: String) {
        val sharedPref = getSharedPreferences()
        val editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }

    protected fun getSharedPreferences(): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    protected fun readSharedPref(key: String): String? {
        val sharedPref = getSharedPreferences()
        return sharedPref.getString(key, null)
    }

    protected fun readSharedPrefList(key: String): Set<String>? {
        val sharedPref = getSharedPreferences()
        return sharedPref.getStringSet(key, null)
    }

    protected fun writeSharedPrefList(key: String, valueList: Set<String>) {
        val sharedPref = getSharedPreferences()
        val editor = sharedPref.edit()
        editor.putStringSet(key, valueList)
        editor.apply()
    }

    /**
     * HELPER VIEW
     */

    protected fun setListLayout(listView: RecyclerView?) {
        if(listView != null) {
            listView.setHasFixedSize(true)
            val gridLayoutManager = StaggeredGridLayoutManager(
                    1, StaggeredGridLayoutManager.VERTICAL)
            listView.layoutManager = gridLayoutManager

            val dividerItemDecoration = DividerItemDecoration(applicationContext,
                    gridLayoutManager.orientation)
            listView.addItemDecoration(dividerItemDecoration)
        }
    }

    protected fun closeKeyboard(){
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}
