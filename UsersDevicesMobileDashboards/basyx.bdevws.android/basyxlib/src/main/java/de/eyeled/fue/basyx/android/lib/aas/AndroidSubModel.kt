package de.eyeled.fue.basyx.android.lib.aas

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

open class AndroidSubModel(bSm: ISubmodel, aAas : AndroidAssetAdministrationShell, loadData : Boolean) {
    var identification: String? = null
    var identificationType: String? = null
    var id: String? = null
    var idShort: String? = null
    var address: String? = null
    var description: LangStrings? = null
    var category: String? = null
    val aAas: AndroidAssetAdministrationShell = aAas
    val baSyxSubModel: ISubmodel = bSm
    val subModelTag: String?

    val operationList: CopyOnWriteArrayList<AndroidOperation> = CopyOnWriteArrayList()
    val dataElementList: CopyOnWriteArrayList<AndroidDataElement> = CopyOnWriteArrayList()

    init {
        idShort = bSm.idShort
        id = bSm.identification?.id
        identification = bSm.identification?.id
        identificationType = getIdType(bSm)
        description = bSm.description
        category = bSm.category
        subModelTag = getSubModelTag(aAas.id, idShort)

        if(loadData) {
            loadSubModelData()
        }
    }

    fun getIdType(bSm: ISubmodel):String?{
        try{
            return bSm.identification?.idType?.name
        }
        catch (ex : Exception){
            Log.e("AndroidSM","error: "+ex.message)
        }

        return null
    }

    fun addOperation(aOp : AndroidOperation){
        if(!operationList.contains(aOp)) {
            operationList.add(aOp)
        }
    }

    fun getOperation(opIdShort:String): AndroidOperation?{
        for(operation in operationList){
            if(operation.idShort.compareTo(opIdShort) == 0){
                return operation;
            }
        }

        return null
    }

    fun addDataElement(aDe : AndroidDataElement){
        if(aDe.modelType?.compareTo("Operation") != 0 && !dataElementList.contains(aDe)) {
            dataElementList.add(aDe)
        }
    }


    fun getDataElement(deIdShort:String): AndroidDataElement?{
        for(dataElement in dataElementList){
            if(dataElement.idShort?.compareTo(deIdShort) == 0){
                return dataElement;
            }
        }

        return null
    }

    /**
     * Retrieve the sub model data by calling its operations and data elements.
     */
    fun loadSubModelData(): AndroidSubModel? {
        try {
            // BaSyx Operations
            val operationMap = baSyxSubModel.operations

            if (operationMap != null && operationMap.isNotEmpty()) {
                for (operation in operationMap.values) {
                    val ao = AndroidOperation(operation)
                    ao.androidSubModel = this
                    val opOut = operation.outputVariables
                    val opIn = operation.inputVariables

                    if (opOut != null && opOut.size > 0) {
                        for (operationVariable in opOut) {
                            // TODO
                        }
                    }

                    if (opIn != null && opIn.size > 0) {
                        for (operationVariable in opIn) {
                            // TODO
                        }
                    }

                    addOperation(ao)
                }
            }

            // load the basyx sub model elements
            val subModelElements = baSyxSubModel.submodelElements

            if (subModelElements != null && subModelElements.isNotEmpty()) {
                for (key in subModelElements.keys) {
                    val element = subModelElements.get(key)
                    if(element != null) {
                        val ade = AndroidDataElement(element, true)
                        ade.androidSubModel = this
                        addDataElement(ade)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("AndroidSubModel", "error loadSubModel: " + e.message)
        }

        return this
    }

    override fun toString(): String {
        return toJson().toString()
    }

    fun toJson(): JsonObject{
        val json = JsonObject()
        try {
            // meta information
            json.addProperty("id", id)
            json.addProperty("idShort", idShort)
            json.addProperty("category", category)

            // data elements
            val elementsList = JsonArray()
            for (subModelElement in dataElementList) {
                elementsList.add(subModelElement.toJson())
            }
            json.add("elements", elementsList)

            // operation data
            val operationsList = JsonArray()
            for (op in operationList) {
                operationsList.add(op.toJson())
            }
            json.add("operations", operationsList)

            val descriptions = JsonArray()
            val languages = description?.languages
            if(languages != null) {
                for (language in languages) {
                    val desc = description?.get(language)
                    if(desc != null){
                        val langDesc = JsonObject()
                        langDesc.addProperty(language, desc)
                        descriptions.add(langDesc)
                    }
                }
            }
            json.add("description", descriptions)

            return json;
        }
        catch(e : java.lang.Exception){
            e.printStackTrace();
            Log.e("AndroidSubModel","error toJson() "+e.message);
        }

        return json
    }

    companion object {
        fun getSubModelTag(aasUrn: String?, smShortId: String?): String {
            if(aasUrn != null && smShortId != null){
                return "$aasUrn:$smShortId"
            }
            else {
                Log.w("AndroidSubMode", "cannot create sub model tag. aas urn or sub model short id is invalid.")
                // return random uuid
                return UUID.randomUUID().toString()
            }
        }
    }
}
