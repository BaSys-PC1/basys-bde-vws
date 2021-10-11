package de.eyeled.fue.basyx.android.bdevws.lib.repository.interfaces

import de.eyeled.fue.basyx.android.bdevws.lib.repository.listener.BdeRepositoryListener
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation
import de.eyeled.fue.basyx.android.lib.repository.interfaces.AasRepository
import de.eyeled.fue.basyx.android.lib.repository.listener.DataListener
import java.util.concurrent.Future


interface BdeAasRepository : AasRepository {
    fun getUniqueAppId(): String
    fun getKafkaAddress(): String?
    fun getDeviceAASId(): String?
    fun getBdeAddress(): String?
    fun removeStreamConnection(listener: BdeRepositoryListener)
    fun invoke(androidOperation: AndroidOperation, listener: BdeRepositoryListener): Any?
    fun onLoginOperation(username : String, password : String, dataListener: DataListener)
    fun registerDevice(deviceServer : String?, force : Boolean): Future<String>
    fun loadDefaultView(userId:String): Future<String>
    fun loadView(userId:String, viewId: String?): Future<String>
    fun writeDefaultView(userId:String, data: String): Future<Boolean>
    fun writeView(userId: String, viewId : String?, data: String): Future<Boolean>
}