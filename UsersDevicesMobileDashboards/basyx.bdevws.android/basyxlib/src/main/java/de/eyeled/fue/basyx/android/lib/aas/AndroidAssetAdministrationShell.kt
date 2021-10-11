package de.eyeled.fue.basyx.android.lib.aas

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings
import java.util.concurrent.CopyOnWriteArrayList

open class AndroidAssetAdministrationShell(aas: IAssetAdministrationShell) {
    var id: String? = null
    var idShort: String? = null
    var address: String? = null
    var description: LangStrings? = null
    var category: String? = null
    var androidSubModelList: CopyOnWriteArrayList<AndroidSubModel> = CopyOnWriteArrayList()
    var aasType: String? = null
    val assetAdministrationShell: IAssetAdministrationShell? = aas

    init {
        id = aas.identification?.id
        idShort = aas.idShort
        description = aas.description
        category = aas.category
    }

    fun hasAAS(): Boolean{
        return assetAdministrationShell != null
    }

    fun hasSubModels(): Boolean{
        return androidSubModelList.isNotEmpty()
    }

    fun addSubModel(subModel : AndroidSubModel){
        androidSubModelList.add(subModel)
    }

    fun getOperation(subModelId:String,operationId:String): AndroidOperation? {
        return getSubModel(subModelId)?.getOperation(operationId)
    }

    open fun getDataElement(subModelId:String,dataElementId:String): AndroidDataElement? {
        return getSubModel(subModelId)?.getDataElement(dataElementId)
    }


    fun getSubModelElements(subModelId:String, dataElementId:String): List<AndroidDataElement>? {
        return getSubModel(subModelId)?.getDataElement(dataElementId)?.collectionDataElements
    }

    fun getDataElementStringValue(dataElement: AndroidDataElement?): String? {
        val propertyValue = dataElement?.property?.value
        if(propertyValue != null && propertyValue is String) {
            return propertyValue
        }

        return null
    }

    fun getSubModel(idShort:String): AndroidSubModel?{
        for(subModel in androidSubModelList){
            if(subModel.idShort?.contains(idShort) == true){
                return subModel
            }
        }

        return null;
    }

    /**
     * Retrieve the sub models for the given Android AAS via BaSys Network
     */
     open fun loadSubModels(){
        try {
            if (hasAAS()) {
                // the getSubModels call will trigger REST calls to the BaSys network
                val subModelMap = assetAdministrationShell?.submodels
                if (subModelMap != null && subModelMap.size > 0) {
                    for (smItem in subModelMap.values) {
                        val aSm = AndroidSubModel(smItem, this, true)
                        addSubModel(aSm)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("AndroidAAS", "error " + e.message)
        }

        onSubModelsLoaded()
    }



    open fun onSubModelsLoaded(){}

    override fun toString(): String {
        return toJson().toString()
    }

    fun toJson(): JsonObject{
        val json = JsonObject()
        try {
            json.addProperty("id", id)
            json.addProperty("idShort", idShort)
            json.addProperty("category", category)
            val subModelList = JsonArray()
            for (subModel in androidSubModelList) {
                subModelList.add(subModel.toJson());
            }
            json.add("submodels", subModelList)

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
        }
        catch(e : java.lang.Exception){
            e.printStackTrace();
            Log.e("AndroidAAS","error toString() "+e.message);
        }

        return json;
    }
}
