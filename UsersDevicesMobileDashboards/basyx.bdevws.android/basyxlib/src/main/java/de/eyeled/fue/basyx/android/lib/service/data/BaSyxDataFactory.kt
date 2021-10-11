package de.eyeled.fue.basyx.android.lib.service.data

import android.util.Log
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell
import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel
import de.eyeled.fue.basyx.android.lib.aas.async.AASLoaderTask
import de.eyeled.fue.basyx.android.lib.aas.async.SMLoaderTask
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell
import java.util.*
import java.util.concurrent.ForkJoinPool
import kotlin.collections.HashMap

open class BaSyxDataFactory {

    open val mIdAasTypeMap = HashMap<String, String>()
    open val mAASLoaderTasks = HashMap<String, AASLoaderTask>()
    open val forkJoinPool = ForkJoinPool()


    open fun loadAaasData(bAas: ConnectedAssetAdministrationShell, loadSubModels: Boolean): AndroidAssetAdministrationShell? {
        return loadAaasData(createAAas(bAas), loadSubModels)
    }

    open fun loadAaasData(aAas: AndroidAssetAdministrationShell?, loadSubModels: Boolean): AndroidAssetAdministrationShell? {
        val aasId = aAas?.id
        if(aasId != null) {
            Log.d("BaSyxDataFactory", "loadAaasData $aasId");

            // check if the aas is already loading
            var loaderTask : AASLoaderTask? = getAasLoaderTask(aasId)

            if(loaderTask == null) {
                // create a new loader task if aas is not loading
                loaderTask = createAasLoaderTask(aAas, loadSubModels, aasId)
            }

            // get the loaded data
            val aas = loaderTask?.join()
            mAASLoaderTasks.remove(aasId)
            return aas
        }

        return null
    }

    @Synchronized fun createAasLoaderTask(aAas: AndroidAssetAdministrationShell?, loadSubModels: Boolean, aasId: String): AASLoaderTask? {
        // revalidate if the aas is already loading
        if (aAas != null && !mAASLoaderTasks.containsKey(aasId)) {
            val loaderTask = AASLoaderTask(aAas, loadSubModels)
            forkJoinPool.execute(loaderTask)
            mAASLoaderTasks[aasId] = loaderTask
            return loaderTask
        }

        return mAASLoaderTasks[aasId]
    }

    @Synchronized fun getAasLoaderTask(aasId: String):AASLoaderTask? {
        if (mAASLoaderTasks.containsKey(aasId)) {
            Log.d("BaSyxDataFactory", "getAasLoaderTask $aasId");
            return mAASLoaderTasks[aasId]
        }

        return null
    }

    open fun loadSubModelData(aAas: AndroidAssetAdministrationShell, smIdShort: String): AndroidSubModel? {
        val subModel = aAas.getSubModel(smIdShort)
        if(subModel != null) {
            val smTask = SMLoaderTask(subModel.baSyxSubModel, aAas, true)
            smTask.fork()
            smTask.join()
            return smTask.androidSm
        }
        return null
    }

    open fun createAAas(bAas: ConnectedAssetAdministrationShell):AndroidAssetAdministrationShell?{
        return AndroidAssetAdministrationShell(bAas)
    }



    /**
     * Set the value for the given data element. This will update the basyx sub model data element
     * via REST
     */
    open fun setDataElement(dataElement: AndroidDataElement): Boolean {
        try {
            if (dataElement.property != null) {
                dataElement.property!!.value = convertValue(dataElement.value, dataElement.valueType)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, e.message!!)
        }

        return false
    }

    companion object {
        private const val TAG = "BaSyxDataFactory"

        fun listContainsAas(aasList: ArrayList<AndroidAssetAdministrationShell>?, urn: String?): Boolean {
            if (aasList != null && urn != null && aasList.isNotEmpty()) {
                for (i in aasList.indices) {
                    if (urn.equals(aasList[i].id, ignoreCase = false)) {
                        return true
                    }
                }
            }
            return false
        }
    }


    /**
     * Converts the given value to the given data type
     */
    open fun convertValue(value: String?, dataType: String?): Any? {
        if (dataType != null) {
            return when (dataType) {
                "double" -> java.lang.Double.valueOf(value!!)
                "float" -> java.lang.Float.valueOf(value!!)
                "int" -> Integer.valueOf(value!!)
                "long" -> java.lang.Long.valueOf(value!!)
                "boolean" -> java.lang.Boolean.valueOf(value)
                else -> value
            }
        } else {
            try {
                return java.lang.Double.valueOf(value!!)
            } catch (e: Exception) {}

            try {
                return java.lang.Boolean.valueOf(value)
            } catch (e: Exception) {}
        }
        return value
    }

    open fun addAasTypeForId(id: String, aasType: String){
        mIdAasTypeMap[id] = aasType
    }

    /**
     * Returns the AASType for the given urn string.
     */
    open fun getAASType(id: String?): String {
        val aasType = mIdAasTypeMap[id]
        if(aasType != null) {
            return aasType
        }
        return BaSyxAasTypes.UNKNOWN_AAS
    }
}