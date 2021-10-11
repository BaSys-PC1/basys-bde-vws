package de.eyeled.fue.basyx.android.aasapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import de.eyeled.fue.basyx.android.aasapp.adapters.DataElementsAdapter
import de.eyeled.fue.basyx.android.aasapp.adapters.OperationsAdapter
import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel
import de.eyeled.fue.basyx.android.bdevws.lib.viewmodel.AasViewModel

class SubModelActivity : DefaultActivity() {

    private var mAasUrn: String? = null
    private var mSmIdShortValue: String? = null

    private var mSmId: TextView? = null
    private var mSmIdShort: TextView? = null
    private var mSmAddress: TextView? = null
    private var mSmDescription: TextView? = null
    private var mDataElementsListView: RecyclerView? = null
    private var mOperationsListView: RecyclerView? = null

    private var mSmWrapper: LinearLayout? = null
    private var mSmProgressWrapper: LinearLayout? = null
    private var mSmIdWrapper: LinearLayout? = null
    private var mDescriptionWrapper: LinearLayout? = null
    private var mDataElementsWrapper: LinearLayout? = null
    private var mOperationsWrapper: LinearLayout? = null

    private var mDataElementsAdapter: DataElementsAdapter? = null
    private var mOperationsAdapter: OperationsAdapter? = null

    private var mRadioButtonWrapper: RadioGroup? = null
    private var mRadioButtonOperations: RadioButton? = null
    private var mRadioButtonDataElements: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_model)

        val intent = intent
        mAasUrn = intent.getStringExtra(AasActivity.URN)
        mSmIdShortValue = intent.getStringExtra(ID_SHORT)

        if (savedInstanceState != null) {
            mAasUrn = savedInstanceState.getString(AasActivity.URN)
            mSmIdShortValue = savedInstanceState.getString(ID_SHORT)
        }

        title = "SubModel ($mSmIdShortValue)"
    }

    override fun onPause() {
        super.onPause()
        mAasViewModel?.removeObservers(this)
    }

    override fun onResume() {
        super.onResume()
        initLayout()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(AasActivity.URN, mAasUrn)
        outState.putString(ID_SHORT, mSmIdShortValue)
        super.onSaveInstanceState(outState)
    }

    private fun initLayout() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mSmWrapper = findViewById(R.id.sm_wrapper)
        mSmProgressWrapper = findViewById(R.id.sm_progress_wrapper)
        mDataElementsWrapper = findViewById(R.id.aas_submodel_dataelements_wrapper)
        mOperationsWrapper = findViewById(R.id.aas_submodel_operations_wrapper)
        mSmIdWrapper = findViewById(R.id.sm_id_wrapper)
        mDescriptionWrapper = findViewById(R.id.sm_description_wrapper)

        mRadioButtonDataElements = findViewById(R.id.radio_button_data_elements)
        mRadioButtonOperations = findViewById(R.id.radio_button_operations)
        mRadioButtonWrapper = findViewById(R.id.radio_button_wrapper)

        mRadioButtonWrapper?.setOnCheckedChangeListener { group, checkedId -> onRadioButtonChanged(checkedId) }
        mRadioButtonDataElements?.isChecked = true

        mSmWrapper?.visibility = View.GONE
        mSmIdWrapper?.visibility = View.GONE
        mDescriptionWrapper?.visibility = View.GONE

        mSmId = findViewById(R.id.sm_id)
        mSmIdShort = findViewById(R.id.sm_id_short)
        mSmAddress = findViewById(R.id.sm_address)
        mSmDescription = findViewById(R.id.sm_description)

        mDataElementsListView = findViewById(R.id.aas_submodel_dataelements_list)
        setListLayout(mDataElementsListView!!)

        mDataElementsAdapter = DataElementsAdapter(applicationContext)
        mDataElementsListView?.adapter = mDataElementsAdapter

        mDataElementsAdapter?.itemClickListener = object : DataElementsAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, buttonType: DataElementsAdapter.ButtonType) {
                if (buttonType === DataElementsAdapter.ButtonType.REFRESH) {
                    val dataElement = mDataElementsAdapter!!.itemList[position]
                    onDataElementClicked(dataElement)
                }
                if (buttonType === DataElementsAdapter.ButtonType.DONE) {
                    val dataElement = mDataElementsAdapter!!.itemList[position]
                    onDataElementUpdate(dataElement)
                }
            }
        }

        mOperationsListView = findViewById(R.id.aas_submodel_operations_list)
        setListLayout(mOperationsListView!!)

        mOperationsAdapter = OperationsAdapter(applicationContext)
        mOperationsListView?.adapter = mOperationsAdapter

        mOperationsAdapter?.itemClickListener = object : OperationsAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val operation = mOperationsAdapter!!.itemList[position]
                onOperationClicked(operation)
            }
        }
    }

    private fun onRadioButtonChanged(id : Int){
        when(id){
            R.id.radio_button_operations -> showOperations()
            R.id.radio_button_data_elements -> showDataElements()
        }
    }

    private fun showOperations(){
        mDataElementsWrapper?.visibility = View.GONE
        mOperationsWrapper?.visibility = View.VISIBLE
    }

    private fun showDataElements(){
        mDataElementsWrapper?.visibility = View.VISIBLE
        mOperationsWrapper?.visibility = View.GONE
    }

    override fun onBaSyxServiceLoaded() {
        if (mAasViewModel != null) {
            mAasViewModel!!.getSubModelLiveData(mAasUrn!!, mSmIdShortValue!!)!!.observe(this, { asm -> onSmDataLoaded(asm) })

            loadSmData()
        }
    }

    private fun onSmDataLoaded(asm: AndroidSubModel?) {
        if (asm != null) {
            mSmWrapper?.visibility = View.VISIBLE
            mSmIdShort?.text = asm.idShort
            if (asm.description != null) {
                mDescriptionWrapper?.visibility = View.VISIBLE
                var descr = asm.description?.get("de-DE")
                if(descr.isNullOrEmpty()){
                    descr = asm.description?.get("en-US")
                }
                mSmDescription?.text = descr
            } else {
                mDescriptionWrapper!!.visibility = View.GONE
            }

            if (asm.identification != null) {
                mSmId?.text = asm.identification
                mSmIdWrapper?.visibility = View.VISIBLE
            } else {
                mSmIdWrapper?.visibility = View.GONE
            }

            mDataElementsAdapter!!.itemList.clear()
            mDataElementsAdapter!!.itemList.addAll(asm.dataElementList)
            mDataElementsAdapter!!.notifyDataSetChanged()

            mOperationsAdapter!!.itemList.clear()
            mOperationsAdapter?.itemList?.addAll(asm.operationList)
            mOperationsAdapter?.notifyDataSetChanged()

            mSmWrapper?.visibility = View.VISIBLE
            mSmProgressWrapper?.visibility = View.GONE
        }
    }

    private fun loadSmData() {
        if (mAasViewModel != null) {
            mAasViewModel?.loadSubModel(mAasUrn!!, mSmIdShortValue!!)
        }
    }

    private fun onOperationClicked(operation: AndroidOperation) {
        if (mAasViewModel != null) {
            mAasViewModel?.getOperationLiveData(operation)?.observe(this, { op -> onOperationLiveDataChange(op) })
            mAasViewModel?.invokeOperation(operation)
        }
    }

    private fun onOperationLiveDataChange(op: AndroidOperation){
        if(op.opResponse != null) {
            val response = op.opResponse.toString()
            showSnackbar("Operation response: $response")
        }
        mOperationsAdapter?.notifyDataSetChanged()
    }

    private fun onDataElementLiveDataChange(dataElement: AndroidDataElement){
        mDataElementsAdapter?.notifyDataSetChanged()
    }


    private fun onDataElementClicked(dataElement: AndroidDataElement) {
        if (mAasViewModel != null) {
            mAasViewModel?.getDataElementLiveData(dataElement)?.observe(this, { de -> onDataElementLiveDataChange(de) })
            mAasViewModel?.loadDataElement(dataElement)
        }
    }

    private fun onDataElementUpdate(dataElement: AndroidDataElement) {
        if (mAasViewModel != null) {
            mAasViewModel!!.setDataElement(dataElement)
        }
    }

    companion object {
        const val ID_SHORT = "id_short"
    }

    override fun getAasViewModelClass(): Class<out AasViewModel> {
        return AasViewModel::class.java
    }
}
