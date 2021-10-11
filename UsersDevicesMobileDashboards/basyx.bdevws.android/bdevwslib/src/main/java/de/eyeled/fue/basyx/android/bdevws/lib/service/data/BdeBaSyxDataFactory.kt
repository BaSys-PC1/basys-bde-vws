package de.eyeled.fue.basyx.android.bdevws.lib.service.data

import android.util.Log
import de.eyeled.fue.basyx.android.bdevws.lib.aas.AnlagenAndroidAAS
import de.eyeled.fue.basyx.android.bdevws.lib.aas.DeviceAndroidAAS
import de.eyeled.fue.basyx.android.bdevws.lib.aas.UserAndroidAAS
import de.eyeled.fue.basyx.android.bdevws.lib.service.data.BdeBaSyxAasTypes.ANLAGEN_AAS_TYPE
import de.eyeled.fue.basyx.android.bdevws.lib.service.data.BdeBaSyxAasTypes.DEVICE_AAS_TYPE
import de.eyeled.fue.basyx.android.bdevws.lib.service.data.BdeBaSyxAasTypes.USER_AAS_TYPE
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell
import de.eyeled.fue.basyx.android.lib.service.data.BaSyxDataFactory
import de.eyeled.fue.basyx.lib.aas.device.DeviceAssetAdministrationShell
import de.eyeled.fue.basyx.lib.aas.iba.AnlagenAssetAdministrationShell
import de.eyeled.fue.basyx.lib.aas.user.UserAssetAdministrationShell
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell

class BdeBaSyxDataFactory : BaSyxDataFactory() {
    private val TAG = "BaSyxDataFactory"

    companion object {
        val IBA_AAS_ID = "urn:com.iba-ag:shells";
        val IBA_AAS_SIGNALS_SM = "Signals";
        val IBA_AAS_SIGNALS_DE = "Signals";
        val IBA_AAS_PUBLISH_OP = "Publish";
    }

    override fun createAAas(bAas: ConnectedAssetAdministrationShell): AndroidAssetAdministrationShell? {
        try {
            val aasType = getAASType(bAas.identification?.id)
            val aAas = when (aasType) {
                USER_AAS_TYPE -> UserAndroidAAS(bAas)
                DEVICE_AAS_TYPE -> DeviceAndroidAAS(bAas)
                ANLAGEN_AAS_TYPE -> AnlagenAndroidAAS(bAas)
                else -> AndroidAssetAdministrationShell(bAas)
            }

            return aAas
        }
        catch (e: Exception){
            e.printStackTrace()
            Log.e(TAG,"createAAS Error: "+e.message)
        }

        return null
    }

    /**
     * Returns the AASType for the given urn string.
     */
    override fun getAASType(id: String?): String {
        if(id != null){
            if(id.contains(UserAssetAdministrationShell.LEGAL_ENTITY+":"+UserAssetAdministrationShell.SUBUNIT)) {
                return USER_AAS_TYPE;
            } else if(id.contains(DeviceAssetAdministrationShell.LEGAL_ENTITY+":"+DeviceAssetAdministrationShell.SUBUNIT)) {
                return DEVICE_AAS_TYPE;
            }
            else if(id.contains(AnlagenAssetAdministrationShell.LEGAL_ENTITY+":"+AnlagenAssetAdministrationShell.SUBUNIT) ||
                    id.startsWith(IBA_AAS_ID)) {
                return ANLAGEN_AAS_TYPE;
            }
        }

        return super.getAASType(id);
    }
}