package de.eyeled.fue.basyx.android.lib.aas

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings
import java.util.concurrent.CopyOnWriteArrayList

class AndroidDataElement {
    var androidSubModel: AndroidSubModel? = null
    var idShort: String? = null
    var value: String? = null
    var valueType: String? = null
    var category: String? = null
    var modelType: String? = null
    var description: LangStrings? = null
    var dataElement: IDataElement? = null
    var subModelElement: ISubmodelElement? = null
    var property: IProperty? = null
    var elementCollection: ISubmodelElementCollection? = null
    var collectionDataElements: CopyOnWriteArrayList<AndroidDataElement> = CopyOnWriteArrayList()
    var error: String? = null

    constructor(subModelElement: ISubmodelElement, loadData: Boolean) {
        this.subModelElement = subModelElement
        idShort = subModelElement.idShort
        category = subModelElement.category
        modelType = subModelElement.modelType
        description = subModelElement.description

        if(subModelElement is IProperty) {
            property = subModelElement
        }
        else if(subModelElement is ISubmodelElementCollection){
            elementCollection = subModelElement
        }

        if(loadData) {
            loadDataElementValue()
        }
    }

    fun loadPropertyValue(): Boolean{
        try {
            value = readValue()
            valueType = property?.valueType?.name
            return true
        } catch (e: Exception) {}

        return false
    }

    private fun readValue(): String?{
        if(property?.value is String){
            return property?.value as String
        }

        return property?.value.toString()
    }

    fun isPropertyElement(): Boolean{
        return property != null
    }

    /**
     * Load the data of the given dataElement. Value will be cast to String
     */
    fun loadDataElementValue(): Boolean {
        try {
            // data element contains either property data or collection data
            if (property != null) {
                return loadPropertyValue()
            }
            else if(elementCollection != null){
                try {
                    val collection = elementCollection?.submodelElements
                    collectionDataElements.clear()

                    if(collection != null){
                        for(element in collection.values){
                            collectionDataElements.add(AndroidDataElement(element, true))
                        }
                    }
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("AndroidDataElement","error "+e.message)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("AndroidDataElement", "error: "+e.message)
        }

        return false
    }

    override fun toString(): String {
        return toJson().toString()
    }

    fun toJson(): JsonObject {
        val json = JsonObject()
        readSubModelElement(this, json)
        return json;
    }

    private fun readSubModelElement(subModelElement: AndroidDataElement?, json: JsonObject) {
        json.addProperty("idShort", subModelElement?.idShort)
        json.addProperty("category", subModelElement?.category)
        json.addProperty("modelType", subModelElement?.modelType)

        if (subModelElement?.valueType != null) {
            try {
                json.addProperty("value", subModelElement.value)
                json.addProperty("valueType", subModelElement.valueType)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (subModelElement?.collectionDataElements?.isNotEmpty() == true) {
            try {
                val elementCollection = subModelElement.collectionDataElements
                val elementCollectionList = JsonArray()
                for (subSubModelElement in elementCollection) {
                    val jsonSubSubModelElement = JsonObject()
                    readSubModelElement(subSubModelElement, jsonSubSubModelElement)
                    elementCollectionList.add(jsonSubSubModelElement)
                }
                json.add("elementCollection", elementCollectionList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


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

    fun getDataElement(deIdShort:String): AndroidDataElement?{
        for (dataElement in collectionDataElements) {
            if (dataElement.idShort?.compareTo(deIdShort) == 0) {
                return dataElement;
            }
        }

        return null
    }
}
