package de.eyeled.fue.basyx.android.bdevws.lib.aas;


import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;

import de.eyeled.fue.basyx.android.bdevws.lib.service.data.BdeBaSyxAasTypes;
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation;


public class DeviceAndroidAAS extends AndroidAssetAdministrationShell {
    public static final String SUBMODEL_COMMUNICATIONS = "Verbindungen";
    public static final String OPERATION_MESSAGING_SERVICE_SUBSCRIBE = "NachrichtenDienstAnmeldung";
    public static final String OPERATION_DATA_STREAMING_SERVICE = "DataStreamingService";
    public static final String PROPERTY_DEVICE_MESSAGE_BROKER = "MessageBroker";

    public DeviceAndroidAAS(IAssetAdministrationShell aas) {
        super(aas);
        setAasType(BdeBaSyxAasTypes.DEVICE_AAS_TYPE);
    }

    public String initMessageBrokerConnection(String appId){
        if(appId != null){
            AndroidOperation aOp = getOperation(SUBMODEL_COMMUNICATIONS, OPERATION_MESSAGING_SERVICE_SUBSCRIBE);
            if(aOp != null){
                try {
                    return (String) aOp.getIOperation().invoke(appId);
                }
                catch (Exception e){}
            }
        }

        return null;
    }
}
