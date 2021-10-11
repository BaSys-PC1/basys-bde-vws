package de.eyeled.fue.basyx.android.bdevws.lib.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.eyeled.fue.basyx.android.bdevws.lib.repository.interfaces.BdeAasRepository
import de.eyeled.fue.basyx.android.bdevws.lib.repository.listener.BdeRepositoryListener
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell
import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel
import de.eyeled.fue.basyx.android.lib.repository.data.BaSyxError
import de.eyeled.fue.basyx.android.lib.repository.interfaces.AasRepository
import de.eyeled.fue.basyx.android.lib.service.BaSyxService
import de.eyeled.fue.basyx.lib.connection.ConnectionProperty


import java.util.HashMap

open class AasViewModel : ViewModel(), BdeRepositoryListener {
    companion object {
        private const val TAG = "AasViewModel"
    }

    protected var mAasRepository: AasRepository? = null
    private var mBdeAasRepository: BdeAasRepository? = null

    private val mAasListLiveData: HashMap<String, MutableLiveData<List<AndroidAssetAdministrationShell>>> = HashMap()
    private val mAasLiveDataHashMap: HashMap<String, MutableLiveData<AndroidAssetAdministrationShell>> = HashMap()
    private val mSubModelLiveDataHashMap: HashMap<String, MutableLiveData<AndroidSubModel>> = HashMap()
    private val mSubModelHashMap: HashMap<AndroidAssetAdministrationShell, MutableLiveData<List<AndroidSubModel>>> = HashMap()
    private val mOperationLiveDataHashMap: HashMap<AndroidOperation, MutableLiveData<AndroidOperation>> = HashMap()
    private val mDataElementLiveDataHashMap: HashMap<AndroidDataElement, MutableLiveData<AndroidDataElement>> = HashMap()
    val mErrorLiveData: MutableLiveData<BaSyxError> = MutableLiveData()

    fun removeObservers(owner : LifecycleOwner){
        for ((_, value) in mAasListLiveData) {
            value.removeObservers(owner)
        }

        for ((_, value) in mAasLiveDataHashMap) {
            value.removeObservers(owner)
        }
        for ((_, value) in mSubModelLiveDataHashMap) {
            value.removeObservers(owner)
        }
        for ((_, value) in mSubModelHashMap) {
            value.removeObservers(owner)
        }
        for ((_, value) in mOperationLiveDataHashMap) {
            value.removeObservers(owner)
            if(!value.hasActiveObservers()){
                mBdeAasRepository?.removeStreamConnection(this)
            }
        }
        for ((_, value) in mDataElementLiveDataHashMap) {
            value.removeObservers(owner)
        }

        mErrorLiveData.removeObservers(owner)
        mAasRepository?.removeRepositoryListener(this)
    }

    fun setAasRepository(aasRepository: AasRepository?) {
        mAasRepository = aasRepository
        mAasRepository?.addRepositoryListener(this)
    }

    fun setBdeAasRepository(aasRepository: BdeAasRepository?) {
        mBdeAasRepository = aasRepository
    }

    fun getAasLiveData(urn: String): MutableLiveData<AndroidAssetAdministrationShell>? {
        val aasLiveData: MutableLiveData<AndroidAssetAdministrationShell>?
        if (mAasLiveDataHashMap.containsKey(urn)) {
            aasLiveData = mAasLiveDataHashMap[urn]
        } else {
            aasLiveData = MutableLiveData()
            mAasLiveDataHashMap[urn] = aasLiveData
        }

        return aasLiveData
    }

    fun getOperationLiveData(op:AndroidOperation?): MutableLiveData<AndroidOperation>? {
        if(op != null) {
            val liveData: MutableLiveData<AndroidOperation>?
            if (mOperationLiveDataHashMap.containsKey(op)) {
                liveData = mOperationLiveDataHashMap[op]
            } else {
                liveData = MutableLiveData()
                mOperationLiveDataHashMap[op] = liveData
            }

            return liveData
        }
        return null
    }

    fun getDataElementLiveData(dataElement:AndroidDataElement?): MutableLiveData<AndroidDataElement>? {
        if(dataElement != null) {
            val liveData: MutableLiveData<AndroidDataElement>?
            if (mDataElementLiveDataHashMap.containsKey(dataElement)) {
                liveData = mDataElementLiveDataHashMap[dataElement]
            } else {
                liveData = MutableLiveData()
                mDataElementLiveDataHashMap[dataElement] = liveData
            }

            return liveData
        }
        return null
    }

    fun getSubModelLiveData(aasUrn: String, smShortId: String): MutableLiveData<AndroidSubModel>? {
        return getSubModelLiveData(AndroidSubModel.getSubModelTag(aasUrn, smShortId))
    }

    private fun getSubModelLiveData(smTag: String): MutableLiveData<AndroidSubModel>? {
        val liveData: MutableLiveData<AndroidSubModel>?
        if (mSubModelLiveDataHashMap.containsKey(smTag)) {
            liveData = mSubModelLiveDataHashMap[smTag]
        } else {
            liveData = MutableLiveData()
            mSubModelLiveDataHashMap[smTag] = liveData
        }
        return liveData
    }


    fun getAasListLiveData(endpoint: String): MutableLiveData<List<AndroidAssetAdministrationShell>>? {
        val list: MutableLiveData<List<AndroidAssetAdministrationShell>>?

        if (mAasListLiveData.containsKey(endpoint)) {
            list = mAasListLiveData[endpoint]
        } else {
            list = MutableLiveData()
            mAasListLiveData[endpoint] = list
        }

        return list
    }

    fun getSubModelListLiveData(aAas: AndroidAssetAdministrationShell): MutableLiveData<List<AndroidSubModel>>? {
        val list: MutableLiveData<List<AndroidSubModel>>?

        if (mSubModelHashMap.containsKey(aAas)) {
            list = mSubModelHashMap[aAas]
        } else {
            list = MutableLiveData()
            mSubModelHashMap[aAas] = list
        }

        return list
    }


    fun loadAasList(endpoint: String, loadSubModels: Boolean) {
        Thread(Runnable {
            if (mAasRepository != null) {
                val liveData = getAasListLiveData(endpoint)
                try {
                    val registry = mAasRepository?.getAasRegistryByEndpoint(endpoint)
                    if(registry != null) {
                        val assetAdministrationShells = mAasRepository?.loadAndGetAasList(registry, loadSubModels, true)
                        liveData?.postValue(assetAdministrationShells)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("AasViewModel", ""+e.message)
                    liveData?.postValue(null)
                }
            }
        }).start()
    }

    fun loadAas(aasUrn: String?) {
        Thread(Runnable {
            if (mAasRepository != null && aasUrn != null) {
                try {
                    val assetAdministrationShell = mAasRepository?.getAas(aasUrn)
                    if (assetAdministrationShell != null) {
                        val liveData = getAasLiveData(aasUrn)
                        liveData!!.postValue(assetAdministrationShell)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("AasViewModel", e.localizedMessage!!)
                }
            }
        }).start()
    }

    fun loadSubModels(aAas: AndroidAssetAdministrationShell, reload: Boolean) {
        if (mAasRepository != null) {
            Thread(Runnable {
                try {
                    if(reload){
                        mAasRepository?.loadSubModelData(aAas)
                    }
                    val liveData = getSubModelListLiveData(aAas)
                    liveData?.postValue(aAas.androidSubModelList)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("AasViewModel", e.localizedMessage!!)
                }
            }).start()
        }
    }

    fun loadSubModel(aasUrn: String, smIdShort: String) {
        Thread(Runnable {
            if (mAasRepository != null) {
                try {
                    val asm = mAasRepository!!.getSubModel(aasUrn, smIdShort)
                    if (asm != null) {
                        val liveData = getSubModelLiveData(aasUrn, smIdShort)
                        liveData!!.postValue(asm)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("AasViewModel", e.localizedMessage!!)
                }
            }
        }).start()
    }

    fun loadDataElement(dataElement: AndroidDataElement?) {
        Thread(Runnable {
            try {
                if (mAasRepository != null && dataElement != null) {
                    mAasRepository!!.loadDataElementValue(dataElement)
                    val liveData = getDataElementLiveData(dataElement)
                    if(liveData != null) {
                        liveData.postValue(dataElement)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, e.localizedMessage!!)
            }
        }).start()
    }


    fun setDataElement(dataElement: AndroidDataElement?) {
        if (mAasRepository != null && dataElement != null) {
            Thread(Runnable {
                try {
                    if (mAasRepository!!.setDataElement(dataElement)) {
                        val asm = dataElement.androidSubModel
                        if (asm != null && asm.subModelTag != null) {
                            val liveData = getSubModelLiveData(asm.subModelTag!!)
                            liveData!!.postValue(asm)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, e.localizedMessage!!)
                }
            }).start()
        }
    }

    fun invokeOperation(operation: AndroidOperation?){
        if (mAasRepository != null && operation != null) {
            Thread(Runnable {
                try {
                    val result = mBdeAasRepository?.invoke(operation, this)
                    val liveData = getOperationLiveData(operation)
                    if(liveData != null) {
                        liveData.postValue(operation)
                    }
                    Log.d(TAG, "" + result)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, e.localizedMessage!!)
                }
            }).start()
        }
    }

    override fun onNewOperationData(property: ConnectionProperty?, operation: AndroidOperation?, data: Any?) {
        Log.d(TAG, "onNewOperationData " + data)
        if (operation != null) {
            operation.opResponse = data
            operation.opResponseType = property?.connectionDataType
            val liveData = getOperationLiveData(operation)
            liveData?.postValue(operation)
        }
    }

    override fun onClientConnection(property: ConnectionProperty?, operation: AndroidOperation?, connected: Boolean) {
        if (property != null && property.id != null) {
            Log.d(TAG, "onClientConnection ")
            operation?.hasClientConnection = connected
            operation?.connectionProperty = property
            val liveData = getOperationLiveData(operation)
            liveData?.postValue(operation)
        }
    }

    override fun onError(error: BaSyxError?) {
        if(error != null){
            mErrorLiveData.postValue(error)
        }
    }

    override fun onStatusUpdated(status: BaSyxService.Status?) {
    }

    override fun onKafkaAddress(address: String?) {
    }

    override fun onBdeAddress(address: String?) {
    }
}