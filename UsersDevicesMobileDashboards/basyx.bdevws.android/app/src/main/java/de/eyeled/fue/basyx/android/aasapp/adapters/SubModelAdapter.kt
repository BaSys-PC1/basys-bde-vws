package de.eyeled.fue.basyx.android.aasapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import de.eyeled.fue.basyx.android.aasapp.R
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel

import java.util.ArrayList

class SubModelAdapter(protected var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListItems: ArrayList<AndroidSubModel>? = null
    var itemClickListener: OnItemClickListener? = null

    val itemList: ArrayList<AndroidSubModel>
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_submodel, parent, false)
        return SubmodelViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sm = mListItems!![position]
        val viewHolder = holder as SubmodelViewHolder
        if (sm.identification != null) {
            viewHolder.id.text = sm.identification
            viewHolder.idWrapper.visibility = View.VISIBLE
        } else {
            viewHolder.idWrapper.visibility = View.GONE
        }

        if (sm.address != null) {
            viewHolder.address.text = sm.address
            viewHolder.addressWrapper.visibility = View.VISIBLE
        } else {
            viewHolder.addressWrapper.visibility = View.GONE
        }

        if (sm.idShort != null) {
            viewHolder.idShort.text = sm.idShort
            viewHolder.idShortWrapper.visibility = View.VISIBLE
        } else {
            viewHolder.idShortWrapper.visibility = View.GONE
        }
    }

    inner class SubmodelViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var listItemWrapper: LinearLayout
        internal var idWrapper: LinearLayout
        internal var idShortWrapper: LinearLayout
        internal var addressWrapper: LinearLayout
        internal var id: TextView
        internal var idShort: TextView
        internal var address: TextView

        init {
            listItemWrapper = itemView.findViewById(R.id.list_item_wrapper)
            idWrapper = itemView.findViewById(R.id.sm_id_wrapper)
            idShortWrapper = itemView.findViewById(R.id.sm_id_short_wrapper)
            addressWrapper = itemView.findViewById(R.id.sm_address_wrapper)
            id = itemView.findViewById(R.id.sm_id)
            idShort = itemView.findViewById(R.id.sm_id_short)
            address = itemView.findViewById(R.id.sm_address)

            listItemWrapper.setOnClickListener {
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(itemView, adapterPosition)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    companion object {
        private val TAG = "SubModelAdapter"
    }
}
