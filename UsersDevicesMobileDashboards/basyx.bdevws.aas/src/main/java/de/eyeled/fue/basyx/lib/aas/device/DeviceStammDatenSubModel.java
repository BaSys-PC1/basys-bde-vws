package de.eyeled.fue.basyx.lib.aas.device;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class DeviceStammDatenSubModel extends BdeSubModel {

	public static final String SUBMODEL_CORE_DATA = "Stammdaten";
	public static final String PROPERTY_DEVICE_ID = "GeraeteId";
	public static final String PROPERTY_IP = "IP";
		
	private String mDeviceId;
	private String mIp;
	
	public DeviceStammDatenSubModel(String deviceId, String ip) {
		super();
		
		mDeviceId = deviceId;
		mIp = ip;
		
		mUrn = new ModelUrn(DeviceAssetAdministrationShell.LEGAL_ENTITY, DeviceAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_CORE_DATA, "1.0", "1", 
				BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	protected void init() {
		setIdShort(SUBMODEL_CORE_DATA);
		setIdentification(IdentifierType.IRI, mUrn.getURN());

		createSubModelProperty(mDeviceId, PROPERTY_DEVICE_ID);
		createSubModelProperty(mIp, PROPERTY_IP);		
	}
	
}
