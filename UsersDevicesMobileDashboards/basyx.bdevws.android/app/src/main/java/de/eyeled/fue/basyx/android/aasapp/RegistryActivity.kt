package de.eyeled.fue.basyx.android.aasapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import de.eyeled.fue.basyx.android.aasapp.adapters.RegistryAdapter
import de.eyeled.fue.basyx.android.lib.helper.PropertyFile
import de.eyeled.fue.basyx.android.bdevws.lib.viewmodel.AasViewModel
import de.eyeled.fue.basyx.android.lib.aas.RegistryElement
import de.eyeled.fue.basyx.android.bdevws.lib.aas.DeviceSetupElement

class RegistryActivity : DefaultActivity() {
    private var mRegistryListView: RecyclerView? = null
    private var mRegistryAdapter: RegistryAdapter? = null
    private var mElementChanged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)
    }

    override fun onResume() {
        super.onResume()
        initLayout()
        readData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.registry_list, menu);

        val menuItem = menu!!.findItem(R.id.menu_change_registry)
        if(menuItem != null) {
            menuItem.setVisible(mElementChanged)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_change_registry -> {
                savePreferences()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initLayout() {
        mFab = findViewById(R.id.fab)
        mFab!!.setOnClickListener { addRegistry() }
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mRegistryListView = findViewById(R.id.registry_list)
        setListLayout(mRegistryListView!!)
        mRegistryAdapter = RegistryAdapter(this)
        mRegistryAdapter!!.changeListener = object : RegistryAdapter.ChangeListener {
            override fun onElementChanged(position: Int) {
                Log.d("Registry","onElementChanged: "+position)
                mElementChanged = true
                invalidateOptionsMenu()
            }
        }
        mRegistryListView!!.adapter = mRegistryAdapter
    }

    private fun addRegistry(){
        addRegistry(RegistryElement())
    }

    private fun addRegistry(element: RegistryElement){
        mRegistryAdapter!!.itemList.add(element)
        mRegistryAdapter!!.notifyDataSetChanged()
    }

    private fun readData(){
        val list = readSharedPrefList(BASYS_REGISTRY_PREF_TAG)

        Log.d("Registry","readData: "+list)
        if(list == null){
            val defaultElement = RegistryElement()
            defaultElement.endpoint = PropertyFile.getProperty("registryServer",this)
            defaultElement.active = true

            val prefRegistryList = HashSet<String>()
            val prefRegistry = getBasysRegistryPrefData(defaultElement)
            prefRegistryList.add(prefRegistry)
            writeSharedPrefList(BASYS_REGISTRY_PREF_TAG, prefRegistryList)
            addRegistry(defaultElement);
        }
        else {
            for(prefElement in list){
                Log.d("Registry","readData Pref Registry Element: "+prefElement)
                val registryElement = getBasysRegistryElementData(prefElement)
                addRegistry(registryElement);
            }
        }
    }

    override fun onBaSyxServiceLoaded() {}

    private fun savePreferences(){
        if(mRegistryAdapter != null){
            val list = HashSet<String>()
            for(element in mRegistryAdapter!!.itemList){
                val prefData = getBasysRegistryPrefData(element)
                list.add(prefData)
            }
            writeSharedPrefList(BASYS_REGISTRY_PREF_TAG, list)
            closeKeyboard()
            Toast.makeText(this,"List saved",Toast.LENGTH_LONG).show()
        }
    }


    companion object {
        val DEVICE_REGISTRATION_PREF_TAG = "deviceRegistration"
        val BASYS_REGISTRY_PREF_TAG = "basysRegistry"

        fun getBasysRegistryPrefData(element: RegistryElement): String{
            return element.endpoint+"|"+element.active
        }

        fun getDeviceRegistrationPrefData(element: DeviceSetupElement): String {
            return element.endpoint+"|"+element.main
        }

        fun getBasysRegistryElementData(prefData: String): RegistryElement{
            val element = RegistryElement();
            val dataList = prefData.split("|")
            element.endpoint = dataList.get(0)
            element.active = dataList.get(1).toBoolean()
            return element;
        }

        fun getDeviceRegistrationElementData(prefData: String): DeviceSetupElement{
            val element = DeviceSetupElement();
            val dataList = prefData.split("|")
            element.endpoint = dataList.get(0)
            element.main = dataList.get(1).toBoolean()
            return element;
        }
    }

    override fun getAasViewModelClass(): Class<out AasViewModel> {
        return AasViewModel::class.java
    }
}
