package de.eyeled.fue.basyx.android.aasapp.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.eyeled.fue.basyx.android.aasapp.R
import de.eyeled.fue.basyx.android.lib.aas.RegistryElement
import java.util.ArrayList



class RegistryAdapter(protected var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListItems: ArrayList<RegistryElement>? = null
    var changeListener: ChangeListener? = null

    val itemList: ArrayList<RegistryElement>
        get() {
            if (mListItems == null) {
                mListItems = ArrayList()
            }

            return mListItems!!
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_registry, parent, false)
        return RegistryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mListItems == null) 0 else mListItems!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mListItems!![position]
        val viewHolder = holder as RegistryViewHolder
        viewHolder.registryEndpoint.text = data.endpoint
        viewHolder.registryActive.setChecked(data.active)
        viewHolder.registryElement = data
    }

//    private fun validateActivated(position: Int): Boolean{
//        if(mListItems != null) {
//            for (i in mListItems!!.indices) {
//                var element = mListItems!!.get(i);
//                if(element.active){
//                    return position == i
//                }
//            }
//        }
//
//        return false
//    }

//    private fun updateSelectedMain(position: Int){
//        if(mListItems != null) {
//            for (i in mListItems!!.indices) {
//                mListItems!!.get(i).main = false;
//            }
//
//            mListItems!!.get(position).main = true
//        }
//        if(changeListener != null) {
//            changeListener!!.onElementChanged(position);
//        }
//        notifyDataSetChanged()
//    }

//    private fun onActivated(position: Int){
//        if(changeListener != null) {
//            changeListener!!.onElementChanged(position);
//        }
//        notifyDataSetChanged()
//    }

    inner class RegistryViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var registryEndpoint: TextView
        internal var registryActive: CheckBox
        internal var registryElement: RegistryElement? = null
        init {
            registryEndpoint = itemView.findViewById(R.id.registry_endpoint)
            registryActive = itemView.findViewById(R.id.registry_active)

            registryActive.setOnCheckedChangeListener{ buttonView, isChecked ->
                Log.d("RegistryAdapter ","OnCheckedChange: "+isChecked + " "+adapterPosition)
                if(registryElement != null){
                    registryElement!!.active = isChecked
                }

                if(changeListener != null) {
                    changeListener!!.onElementChanged(adapterPosition);
                }
            }

            registryEndpoint.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d("RegistryAdapter ","onTextChanged: "+text)
                    if(registryElement != null) {
                        registryElement!!.endpoint = text.toString()

                        if(changeListener != null) {
                            changeListener!!.onElementChanged(adapterPosition);
                        }
                    }
                }
            })
        }
    }

    interface ChangeListener{
        fun onElementChanged(position: Int)
    }
}