package de.eyeled.fue.basyx.android.aasapp

import android.content.Intent
import android.os.Bundle

import de.eyeled.fue.basyx.android.aasapp.adapters.SubModelAdapter

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import de.eyeled.fue.basyx.android.bdevws.lib.viewmodel.AasViewModel
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel

class AasActivity : DefaultActivity() {

    private var mAasId: TextView? = null
    private var mAasIdShort: TextView? = null
    private var mAasAddress: TextView? = null
    private var mAasDescription: TextView? = null
    private var mSmListView: RecyclerView? = null
    private var mSmListProgress: ProgressBar? = null
    private var mSubModelAdapter: SubModelAdapter? = null
    private var mAasUrn: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aas)

        checkUrnValue(savedInstanceState)

        title = "AAS ($mAasUrn)"
    }

    override fun onResume() {
        super.onResume()

        val intent = intent
        if (intent.hasExtra(URN)) {
            mAasUrn = intent.getStringExtra(URN)
        }

        initLayout()
    }

    private fun checkUrnValue(savedInstanceState: Bundle?) {
        // Get the Intent that started this activity and extract the string
        val intent = intent
        mAasUrn = intent.getStringExtra(URN)

        if (savedInstanceState != null) {
            mAasUrn = savedInstanceState.getString(URN)
        }

        if (mAasUrn == null) {
            mAasUrn = readSharedPref(URN)
            removeSharedPref(URN)
        }
    }

    override fun onPause() {
        super.onPause()

        writeSharedPref(URN, mAasUrn!!)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(URN, mAasUrn)
        super.onSaveInstanceState(outState)
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mAasUrn = savedInstanceState.getString(URN)
    }

    private fun startSubmodelActivity(idShort: String?) {
        val intent = Intent(this, SubModelActivity::class.java)
        intent.putExtra(URN, mAasUrn)
        intent.putExtra(SubModelActivity.ID_SHORT, idShort)
        startActivity(intent)
    }

    private fun initLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "AAS ($mAasUrn)"

        mFab = findViewById(R.id.fab)
        mFab!!.setOnClickListener { }
        mAasId = findViewById(R.id.aas_id)
        mAasIdShort = findViewById(R.id.aas_id_short)
        mAasAddress = findViewById(R.id.aas_address)
        mAasDescription = findViewById(R.id.aas_description)

        mSmListProgress = findViewById(R.id.aas_submodel_list_progress)
        mSmListView = findViewById(R.id.aas_submodel_list)
        setListLayout(mSmListView!!)

        mSubModelAdapter = SubModelAdapter(applicationContext)
        mSmListView!!.adapter = mSubModelAdapter

        mSubModelAdapter!!.itemClickListener = object : SubModelAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Log.d(TAG, "onItemClick $position")
                val submodel = mSubModelAdapter!!.itemList[position]
                startSubmodelActivity(submodel.idShort)
            }
        }

        if (mBaSyxService != null && mAasViewModel != null) {
            loadAasData()
        }
    }

    override fun onBaSyxServiceLoaded() {
        if (mAasViewModel != null && mAasUrn != null) {
            mAasViewModel?.getAasLiveData(mAasUrn!!)?.observe(this, Observer { aAas ->
                // Update the UI, in this case, a TextView.
                onAasDataLoaded(aAas)

                mAasViewModel?.getSubModelListLiveData(aAas)?.observe(this,
                    Observer{
                        submodels -> onSubModelDataLoaded(submodels)
                })
                loadSmData(aAas)
            })

            loadAasData()
        }
    }

    private fun loadAasData() {
        Log.d(TAG, "loadAasData " + mAasUrn!!)
        if (mAasViewModel != null && mAasUrn != null) {
            mAasViewModel?.loadAas(mAasUrn!!)
        }
    }

    private fun loadSmData(aaas: AndroidAssetAdministrationShell) {
        Log.d(TAG, "loadSmData " + aaas.id!!)
        if (mAasViewModel != null) {
            mAasViewModel?.loadSubModels(aaas,false)
        }
    }

    private fun onAasDataLoaded(aaas: AndroidAssetAdministrationShell?) {
        Log.d(TAG, "onAasDataLoaded " + aaas!!)
        mAasId?.text = aaas.id
        mAasIdShort?.text = aaas.idShort
        mAasAddress?.text = aaas.address

        if (aaas.description != null) {
            var descr = aaas.description?.get("de-DE")
            if(descr.isNullOrEmpty()){
                descr = aaas.description?.get("en-US")
            }
            mAasDescription?.text = descr
        }
    }

    private fun onSubModelDataLoaded(submodelList: List<AndroidSubModel>?) {
        Log.d(TAG, "onSubModelDataLoaded " + submodelList!!)
        mSubModelAdapter?.itemList?.clear()
        mSubModelAdapter?.itemList?.addAll(submodelList)
        mSubModelAdapter?.notifyDataSetChanged()

        mSmListView?.setVisibility(View.VISIBLE)
        mSmListProgress?.setVisibility(View.GONE)
    }

    companion object {
        private val TAG = "AasActivity"
        val URN = "aas_urn"
    }

    override fun getAasViewModelClass(): Class<out AasViewModel> {
        return AasViewModel::class.java
    }
}
