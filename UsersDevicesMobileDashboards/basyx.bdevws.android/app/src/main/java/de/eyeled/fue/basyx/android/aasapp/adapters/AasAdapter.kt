package de.eyeled.fue.basyx.android.aasapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import de.eyeled.fue.basyx.android.aasapp.R
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell

import java.util.ArrayList

class AasAdapter(protected var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListItems: ArrayList<AndroidAssetAdministrationShell>? = null
    var itemClickListener: OnItemClickListener? = null

    val itemList: ArrayList<AndroidAssetAdministrationShell>
        get() {
            if (mListItems == null) {
                mListItems = ArrayList()
            }

            return mListItems!!
        }

    override fun getItemCount(): Int {
        return if (mListItems == null) 0 else mListItems!!.size
    }

    @Synchronized
    fun addItem(aas : AndroidAssetAdministrationShell){
        val aasId = aas.id;
        if(aasId != null && !itemList.any { it.id != null && it.id!!.compareTo(aasId) == 0 }){
            itemList.add(aas)
        }
    }

    @Synchronized
    fun removeItem(aas : AndroidAssetAdministrationShell){
        itemList.remove(aas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_aas, parent, false)
        return AasViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val aaas = mListItems!![position]
        val aasViewHolder = holder as AasViewHolder
        aasViewHolder.aasId.text = aaas.id
        aasViewHolder.aasIdShort.text = aaas.idShort
        aasViewHolder.aasAddress.text = aaas.address
    }

    inner class AasViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var aasListItemWrapper: LinearLayout
        internal var aasId: TextView
        internal var aasIdShort: TextView
        internal var aasAddress: TextView
        init {
            aasListItemWrapper = itemView.findViewById(R.id.aas_list_item_wrapper)
            aasId = itemView.findViewById(R.id.aas_id)
            aasIdShort = itemView.findViewById(R.id.aas_id_short)
            aasAddress = itemView.findViewById(R.id.aas_address)

            aasListItemWrapper.setOnClickListener {
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(itemView, adapterPosition)
                }
            }
        }
    }

    /**
     * Interface for the onItemClick and onItemLongClick methods
     */
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    companion object {
        private val TAG = "AasAdapter"
    }
}
