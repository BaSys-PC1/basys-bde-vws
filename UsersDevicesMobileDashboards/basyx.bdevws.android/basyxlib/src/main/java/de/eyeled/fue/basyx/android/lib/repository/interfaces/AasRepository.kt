package de.eyeled.fue.basyx.android.lib.repository.interfaces

import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell
import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel
import de.eyeled.fue.basyx.android.lib.repository.listener.RepositoryListener
import de.eyeled.fue.basyx.android.lib.repository.operations.InvokeOperation
import de.eyeled.fue.basyx.android.lib.service.BaSyxService
import de.eyeled.fue.basyx.android.lib.service.components.RegistryComponent
import java.util.concurrent.FutureTask

interface AasRepository {

    fun loadAndGetAasList(registryComponent: RegistryComponent, loadSubModels : Boolean, useCached: Boolean): List<AndroidAssetAdministrationShell>?

    fun getAasList(aasType: String): List<AndroidAssetAdministrationShell>?

    fun getAas(id: String): AndroidAssetAdministrationShell?

    // load and get only the AAS object for the given aas id
    fun loadAndGetAas(id: String): AndroidAssetAdministrationShell?

    // load and get only the AAS object for the given aas id
    fun loadAndGetAas(id: String, loadSubModels:Boolean): AndroidAssetAdministrationShell?

    // get only the AAS object for the given aas id
    fun getCachedAAas(id: String): AndroidAssetAdministrationShell?

    // get only the AAS object for the given aas id
    fun getCachedAAas(type: String, id: String): AndroidAssetAdministrationShell?

    // load the sub models for the given aas
    fun loadSubModelData(aAas: AndroidAssetAdministrationShell)

    // load the sub models for the given aas
    fun loadSubModelData(aAas: AndroidAssetAdministrationShell, smIdShort: String)

    // load and get the sub model object for the given aas urn and sub model id
    fun loadAndGetSubModel(aasId: String, smIdShort: String): AndroidSubModel?

    // get the sub model object for the given aas urn and sub model id
    fun getSubModel(aasId: String, smIdShort: String): AndroidSubModel?

    // load the data element value
    fun loadDataElementValue(dataElement: AndroidDataElement)

    // set the data element value
    fun setDataElement(dataElement: AndroidDataElement): Boolean

    // invoke operation for the given android op
    fun invoke(androidOperation: AndroidOperation, param: String?): Any?

    // invoke operation for the given android op
    fun invoke(androidOperation: AndroidOperation): Any?

    // invoke operation for the given invoke op
    fun invoke(invokeOperation: InvokeOperation, vararg params: Any?): Any?

    fun getAasRegistryByType(aasType: String): RegistryComponent?
    fun getAasRegistryByEndpoint(endpoint: String): RegistryComponent?
    fun getAasRegistryByAas(aasId: String): RegistryComponent?

    fun addRepositoryListener(listener: RepositoryListener)
    fun removeRepositoryListener(listener: RepositoryListener)

    fun getStatus() : BaSyxService.Status

    fun clearBasysCache();
    fun clearRegistryEndpoints();
}
