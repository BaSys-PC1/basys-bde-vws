package de.eyeled.fue.basyx.android.bdevws.lib.viewmodel

import androidx.lifecycle.MutableLiveData
import de.eyeled.fue.basyx.android.bdevws.lib.aas.UserAndroidAAS
import de.eyeled.fue.basyx.android.bdevws.lib.repository.interfaces.BdeAasRepository
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell
import de.eyeled.fue.basyx.android.lib.repository.listener.DataListener
import de.eyeled.fue.basyx.lib.context.data.ServiceResponse
import org.json.JSONObject

class UserAasViewModel : AasViewModel(){
    var mUserLoginCheckLiveData : MutableLiveData<UserAndroidAAS> ? = null

    init {
        mUserLoginCheckLiveData = MutableLiveData()
    }

    fun checkUserLogin(uName:String, password:String){
        if (mAasRepository != null) {
            if(mAasRepository is BdeAasRepository){
                val dataListener = DataListener {data ->
                    Thread(Runnable {
                        var postValue : UserAndroidAAS ? = null
                        if(data is JSONObject){
                            val serviceResponse = ServiceResponse.create(data.toString())
                            if(serviceResponse != null && "login".compareTo(serviceResponse.service) == 0 &&
                                    serviceResponse.isSuccess) {
                                val aasId = serviceResponse.getDataString("aas")
                                val aas: AndroidAssetAdministrationShell? = mAasRepository?.loadAndGetAas(aasId, true)
                                if (aas is UserAndroidAAS) {
                                    postValue = aas
                                }
                            }
                        }
                        mUserLoginCheckLiveData!!.postValue(postValue)
                    }).start()
                }
                (mAasRepository as BdeAasRepository).onLoginOperation(uName, password, dataListener);
            }
        }
    }
}