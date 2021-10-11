package de.eyeled.fue.basyx.android.lib.aas

import android.util.Log
import com.google.gson.JsonObject
import de.eyeled.fue.basyx.lib.connection.ConnectionProperty
import de.eyeled.fue.basyx.lib.connection.data.DataType
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings
import java.util.concurrent.CopyOnWriteArrayList

class AndroidOperation {
    var idShort: String
    var category: String? = null
    var modelType: String
    var opInput: String? = null
    var opResponse: Any? = null
    var opResponseType: DataType? = null
    var description: LangStrings? = null
    var iOperation: IOperation? = null
    var androidSubModel: AndroidSubModel? = null
    var connectionProperty : ConnectionProperty? = null
    var hasClientConnection : Boolean = false
    var inVars : List<AndroidOperationVariable>
    var outVars : List<AndroidOperationVariable>
    var inOutVars : List<AndroidOperationVariable>

    constructor(iOperation: IOperation) {
        this.iOperation = iOperation
        idShort = iOperation.idShort
        category = iOperation.category
        modelType = iOperation.modelType
        description = iOperation.description

        inVars = CopyOnWriteArrayList()
        outVars = CopyOnWriteArrayList()
        inOutVars = CopyOnWriteArrayList()
    }

    override fun toString(): String {
        return toJson().toString()
    }

    fun toJson(): JsonObject {
        val jsonOp = JsonObject()
        try {
            jsonOp.addProperty("idShort", idShort)
            jsonOp.addProperty("category", category)
            jsonOp.addProperty("modelType", modelType)
            if(opResponse is String) {
                jsonOp.addProperty("opResponse", opResponse as String)
                jsonOp.addProperty("opResponseType", opResponseType?.name)
            }

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
            Log.e("AndroidOperation","error toJson() "+e.message);
        }

        return jsonOp
    }
}
