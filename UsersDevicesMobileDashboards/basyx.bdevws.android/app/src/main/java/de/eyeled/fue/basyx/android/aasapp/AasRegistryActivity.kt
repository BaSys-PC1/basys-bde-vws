package de.eyeled.fue.basyx.android.aasapp

import android.content.Intent
import android.os.Bundle

import de.eyeled.fue.basyx.android.aasapp.adapters.AasAdapter

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import de.eyeled.fue.basyx.android.bdevws.lib.viewmodel.AasViewModel
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell

class AasRegistryActivity : DefaultActivity() {

    private var mAasListView: RecyclerView? = null
    private var mAasAdapter: AasAdapter? = null
    private var mProgress: ProgressBar? = null
    private var mAasListInfo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aas_registry)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu")
        var menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.aas_registry, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected")
        when (item.itemId) {
            R.id.menu_change_registry -> {
                startActivity(Intent(this, RegistryActivity::class.java))
                return true
            }
            R.id.menu_user_login -> {
                startActivity(Intent(this, UserLoginActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        initLayout()
        if (mBaSyxService != null && mAasViewModel != null) {
            observeAndLoadData()
        }
    }

    private fun initLayout() {
        mFab = findViewById(R.id.fab)
        mFab!!.setOnClickListener { loadData() }

        mAasListView = findViewById(R.id.aas_list)
        mProgress = findViewById(R.id.aas_progress)
        mAasListInfo = findViewById(R.id.aas_info)
        setListLayout(mAasListView)

        mAasAdapter = AasAdapter(applicationContext)
        mAasListView?.adapter = mAasAdapter

        mAasAdapter?.itemClickListener = object : AasAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val aaas = mAasAdapter?.itemList?.get(position)
                startAasActivity(aaas?.id)
            }
        }
    }

    private fun startAasActivity(urn: String?) {
        if(urn != null) {
            val intent = Intent(this, AasActivity::class.java)
            intent.putExtra(AasActivity.URN, urn)
            startActivity(intent)
        }
    }

    private fun loadData() {
        if (mAasViewModel != null) {
            mAasAdapter?.itemList?.clear()
            if (mFab != null) {
                mFab?.hide()
            }
            mAasListView?.visibility = View.GONE

            var loadingData = false

            for (registryElement in mRegistryServerList) {
                if (registryElement.active) {
                    loadingData = true
                    mAasViewModel?.loadAasList(registryElement.endpoint!!, true)
                }
            }

            if(loadingData) {
                mProgress?.setVisibility(View.VISIBLE)
                mAasListInfo?.text = "loading data"
            }
            else {
                mProgress?.setVisibility(View.GONE)
                mAasListInfo?.text = "no data available"
                mFab?.show()
            }
        }
    }

    private fun onListChange() {
        val handler = Handler(mainLooper)
        handler.post {
            mAasAdapter?.notifyDataSetChanged()
            mAasListView?.setVisibility(View.VISIBLE)
            mProgress?.setVisibility(View.GONE)
        }

        if (mFab != null) {
            mFab?.show()
        }
    }

    override fun onBaSyxServiceLoaded() {
        observeAndLoadData()
    }

    fun observeAndLoadData(){
        Log.d(TAG,"observeAndLoadData ")
        if (mAasViewModel != null) {
            var observer = Observer<List<AndroidAssetAdministrationShell>?> { aasList ->
                if(aasList != null) {
                    for(aas in aasList){
                        mAasAdapter?.addItem(aas)
                        Log.d(TAG,"observeAndLoadData "+aas)
                    }
                }
                onListChange()
            }

            for(registryElement in mRegistryServerList){
                if(registryElement.active) {
                    mAasViewModel?.getAasListLiveData(registryElement.getType())?.observe(this, observer)
                }
            }

            loadData()
        }
    }

    override fun onPause() {
        super.onPause()
        removeObserver()
    }

    fun removeObserver(){
        for(registryElement in mRegistryServerList){
            if(registryElement.active) {
                mAasViewModel?.getAasListLiveData(registryElement.getType())?.removeObservers(this)
            }
        }
    }

    companion object {
        private val TAG = "AasRegistryActivity"
    }

    override fun getAasViewModelClass(): Class<out AasViewModel> {
        return AasViewModel::class.java
    }
}
