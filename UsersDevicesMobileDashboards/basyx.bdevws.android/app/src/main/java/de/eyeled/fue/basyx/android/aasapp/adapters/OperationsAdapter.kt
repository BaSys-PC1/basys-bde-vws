package de.eyeled.fue.basyx.android.aasapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.EditorInfo.IME_ACTION_NEXT
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import de.eyeled.fue.basyx.android.aasapp.R
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperationVariable
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class OperationsAdapter(protected var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListItems: ArrayList<AndroidOperation>? = null
    var itemClickListener: OnItemClickListener? = null

    val itemList: ArrayList<AndroidOperation>
        get() {
            if (mListItems == null) {
                mListItems = ArrayList()
            }

            return mListItems!!
        }

    override fun getItemCount(): Int {
        return if (mListItems == null) 0 else mListItems!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_operation, parent, false)
        return OperationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val el = mListItems!![position]
        val viewHolder = holder as OperationViewHolder
        viewHolder.idShort.text = el.idShort
        viewHolder.opInputWrapper.removeAllViews()
        if(el.inVars.isNotEmpty()){
            for(index in el.inVars.indices){
                val inVar = el.inVars[index]
                var editText = viewHolder.inEditMap[inVar]
                if(editText == null) {
                    editText = EditText(mContext)
                    editText.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                    viewHolder.inEditMap[inVar] = editText
                }
                if(inVar.idShort != null) {
                    editText.hint = inVar.idShort
                }
                else {
                    editText.hint = null
                }

                if(inVar.data != null) {
                    editText.setText(inVar.data)
                }
                if(index == el.inVars.lastIndex){
                    editText.imeOptions = IME_ACTION_DONE
                }
                else {
                    editText.imeOptions = IME_ACTION_NEXT
                }
                editText.setLines(1)
                editText.isSingleLine = true
                viewHolder.opInputWrapper.addView(editText)
            }
        }

        viewHolder.opOutputWrapper.removeAllViews()
        if(el.outVars.isNotEmpty()){
            for(outVar in el.outVars){
                val text = TextView(mContext)
                text.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                if(outVar.data != null) {
                    text.text = outVar.data
                }
                viewHolder.opOutputWrapper.addView(text)
            }
        }
        viewHolder.operationResponse.text = el.opResponse?.toString()

        if(el.hasClientConnection){
            viewHolder.opConnectionWrapper.visibility = View.VISIBLE
        }else{
            viewHolder.opConnectionWrapper.visibility = View.GONE
        }
    }

    inner class OperationViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var listItemWrapper: RelativeLayout
        internal var idShort: TextView
        internal var opInputWrapper: LinearLayout
        internal var opOutputWrapper: LinearLayout
        internal var opConnection: EditText
        internal var opConnectionWrapper: LinearLayout
        internal var operationButton: Button
        internal var operationResponse: TextView
        internal var inEditMap: HashMap<AndroidOperationVariable, EditText>

        init {
            listItemWrapper = itemView.findViewById(R.id.list_item_wrapper)
            idShort = itemView.findViewById(R.id.operation_id_short)
            opConnection = itemView.findViewById(R.id.operation_connection_input)
            opConnectionWrapper = itemView.findViewById(R.id.operation_connection_wrapper)
            opInputWrapper = itemView.findViewById(R.id.operation_input_wrapper)
            opOutputWrapper = itemView.findViewById(R.id.operation_response_wrapper)
            operationResponse = itemView.findViewById(R.id.operation_response)
            operationButton = itemView.findViewById(R.id.operation_action)

            inEditMap = HashMap()

            operationButton.setOnClickListener {
                if (itemClickListener != null) {
                    for(inVar in mListItems!![adapterPosition].inVars){
                        val editText = inEditMap[inVar]
                        if(editText != null){
                            inVar.data = editText.text.toString()
                        }
                    }
                    itemClickListener!!.onItemClick(itemView, adapterPosition)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    companion object {
        private val TAG = "DataElementsAdapter"
    }
}