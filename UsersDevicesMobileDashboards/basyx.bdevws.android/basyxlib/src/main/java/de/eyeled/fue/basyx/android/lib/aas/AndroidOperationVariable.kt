package de.eyeled.fue.basyx.android.lib.aas

import android.util.Log
import com.google.gson.JsonObject
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property

class AndroidOperationVariable {
    var iOperationValue: IOperationVariable
    var idShort: String?
    var category: String?
    var valueType: String?
    var description: LangStrings? = null
    var data: String? = null

    constructor(iOperation: IOperationVariable) {
        this.iOperationValue = iOperation

        val value : ISubmodelElement = iOperation.value

        this.idShort = value.idShort
        this.category = value.category
        this.description = value.description
        this.valueType = null

        if(value is Property){
            try {
                this.valueType = value.valueType.name
            }
            catch(ex:Exception){
                this.valueType = "String"
            }
        }
    }

    override fun toString(): String {
        return toJson().toString()
    }

    fun toJson(): JsonObject {
        val jsonOp = JsonObject()
        try {
            jsonOp.addProperty("idShort", idShort)
            jsonOp.addProperty("category", category)
            jsonOp.addProperty("valueType", valueType)
            jsonOp.addProperty("data", data)

            if(!description.isNullOrEmpty()){
                val langJson = JsonObject()
                for(lang in description!!.languages){
                    langJson.addProperty(lang,description?.get(lang))
                }
                jsonOp.add("description",langJson)
            }
        }
        catch(e : java.lang.Exception){
            e.printStackTrace();
            Log.e("AndroidOperationVar","error toJson() "+e.message);
        }

        return jsonOp
    }
}
