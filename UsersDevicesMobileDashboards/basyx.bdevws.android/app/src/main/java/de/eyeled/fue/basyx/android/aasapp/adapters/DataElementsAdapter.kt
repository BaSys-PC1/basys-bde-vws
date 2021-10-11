package de.eyeled.fue.basyx.android.aasapp.adapters

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import de.eyeled.fue.basyx.android.aasapp.R
import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement
import java.util.*


class DataElementsAdapter(protected var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListItems: ArrayList<AndroidDataElement>? = null
    var itemClickListener: OnItemClickListener? = null

    val itemList: ArrayList<AndroidDataElement>
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_data_element, parent, false)
        return DataElementViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val el = mListItems!![position]
        val viewHolder = holder as DataElementViewHolder
        viewHolder.idShort.text = el.idShort
        viewHolder.value.setText(el.value)

        viewHolder.elementCollectionList.removeAllViews()

        if(el.collectionDataElements.size > 0){
            viewHolder.elementCollectionWrapper.visibility = View.VISIBLE
            viewHolder.dataElementValueWrapper.visibility = View.GONE

            for(dataElement in el.collectionDataElements){
                var text = "<b>Element Id:</b> " + dataElement.idShort
                if(dataElement.value != null){
                    text += " | Value: " + dataElement.value
                }
                addTextView(text, viewHolder.elementCollectionList, 50, 0, 10, 5)
                if(dataElement.collectionDataElements.size > 0){
                    for(subDataElement in dataElement.collectionDataElements){
                        addTextView( "<b>"+subDataElement.idShort + "</b> : " + subDataElement.value,
                                viewHolder.elementCollectionList, 80, 0, 5, 5)
                    }
                }
                addSeparator(viewHolder.elementCollectionList)
            }
        }
        else {
            viewHolder.elementCollectionWrapper.visibility = View.GONE;
            viewHolder.dataElementValueWrapper.visibility = View.VISIBLE
        }
    }

    fun addTextView(text: String, listView: LinearLayout, marginStart: Int, marginEnd: Int, marginTop: Int, marginBottom: Int){
        val subElementView = TextView(this.mContext)
        subElementView.text = Html.fromHtml(text)
        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(marginStart, marginTop, marginEnd, marginBottom)
        subElementView.layoutParams = params
        listView.addView(subElementView);
    }

    fun addSeparator(listView: LinearLayout){
        val subElementView = View(this.mContext)
        subElementView.setBackgroundColor(Color.BLACK)
        subElementView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        subElementView.layoutParams.height = 2
        listView.addView(subElementView);
    }

    inner class DataElementViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var listItemWrapper: RelativeLayout
        internal var idShort: TextView
        internal var value: EditText
        internal var dataElementValueWrapper: LinearLayout
        internal var dataElementButtonRefresh: ImageView
        internal var dataElementButtonEdit: ImageView
        internal var dataElementButtonDone: ImageView


        internal var elementCollectionWrapper: LinearLayout
        internal var elementCollectionList: LinearLayout
        internal var elementCollectionListInfo: TextView

        internal var collectionExpanded: Boolean = false

        init {
            listItemWrapper = itemView.findViewById(R.id.list_item_wrapper)
            idShort = itemView.findViewById(R.id.data_element_id_short)
            value = itemView.findViewById(R.id.data_element_value)
            dataElementValueWrapper = itemView.findViewById(R.id.data_element_value_wrapper)
            dataElementButtonRefresh = itemView.findViewById(R.id.data_element_button_refresh)
            dataElementButtonEdit = itemView.findViewById(R.id.data_element_button_edit)
            dataElementButtonDone = itemView.findViewById(R.id.data_element_button_done)

            elementCollectionWrapper = itemView.findViewById(R.id.element_collection_list_wrapper)
            elementCollectionList = itemView.findViewById(R.id.element_collection_list)
            elementCollectionListInfo = itemView.findViewById(R.id.element_collection_list_info)

            dataElementButtonRefresh.setOnClickListener {
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(itemView, adapterPosition, ButtonType.REFRESH)
                }
            }

            dataElementButtonEdit.setOnClickListener {
                value.isEnabled = true
                dataElementButtonEdit.visibility = View.GONE
                dataElementButtonDone.visibility = View.VISIBLE
            }

            dataElementButtonDone.setOnClickListener {
                if (itemClickListener != null) {
                    mListItems!![adapterPosition].value = value.text.toString()
                    itemClickListener!!.onItemClick(itemView, adapterPosition, ButtonType.DONE)
                    value.isEnabled = false
                    dataElementButtonDone.visibility = View.GONE
                    dataElementButtonEdit.visibility = View.VISIBLE
                }
            }

            elementCollectionWrapper.setOnClickListener{
                collectionExpanded = !collectionExpanded
                if(collectionExpanded){
                    elementCollectionList.visibility = View.VISIBLE
                    elementCollectionListInfo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_keyboard_arrow_down_black_18, 0, 0, 0);
                }
                else {
                    elementCollectionList.visibility = View.GONE
                    elementCollectionListInfo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_keyboard_arrow_right_24, 0, 0, 0);

                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, buttonType: ButtonType)
    }

    enum class ButtonType {
        REFRESH, EDIT, DONE
    }

    companion object {
        private val TAG = "DataElementsAdapter"
    }
}