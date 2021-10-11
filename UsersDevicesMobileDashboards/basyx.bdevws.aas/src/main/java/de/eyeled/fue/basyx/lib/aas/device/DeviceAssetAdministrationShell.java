package de.eyeled.fue.basyx.lib.aas.device;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.device.DeviceVerbinungsProfilSubModel.DeviceProperties;

public class DeviceAssetAdministrationShell extends BdeAdmistrationShell {

	public static final String LEGAL_ENTITY = "de.eyeled";
	public static final String SUBUNIT = "basys.bdevws.device";
	private static final String DEVICE_AAS_TYPE_SHORT = "DeviceAASType";
	private static ModelUrn sDeviceAasTypeUrn = new ModelUrn(LEGAL_ENTITY, SUBUNIT, "aas", "1.0", "1", "deviceAasType", "001");	
	
	private static DeviceAssetAdministrationShell sAasType;
	
	private DeviceStammDatenSubModel mStammdatenSubModel;	
	private DeviceVerbinungsProfilSubModel mVerbindungsprofilSubModel;
	private DeviceConnectionSubModel mDeviceConnectionSubModel;
	
	private DeviceAssetAdministrationShell() {}

	public static DeviceAssetAdministrationShell type() {		
		if(sAasType == null) {
			sAasType = new DeviceAssetAdministrationShell();
			sAasType.setIdentification(IdentifierType.IRI, sDeviceAasTypeUrn.getURN());
			sAasType.setIdShort(DEVICE_AAS_TYPE_SHORT);
			sAasType.setAsset(DeviceAsset.type());
			sAasType.createDescriptors();
			sAasType.createProvider();
		}
		
		return sAasType;
	}
	

	public DeviceAssetAdministrationShell createDeviceAasInstance(
			String idShort, String endpoint, String endpointType, String endpointMapping,
			Asset deviceAsset, LangStrings description) {
		String aasUrn = new ModelUrn("de.eyeled", "basys.bdevws.device", "aas", "1.0", "1", idShort, "001").getURN();		
		DeviceAssetAdministrationShell instance = new DeviceAssetAdministrationShell();
		instance.setIdentification(IdentifierType.IRI, aasUrn);
		instance.setIdShort(idShort);
		instance.setDescription(description);
//		instance.setDerivedFrom(Reference.createAsFacade(this));
		instance.setAsset(deviceAsset);
		instance.setEndpoint(endpoint, endpointMapping+idShort, endpointType);
		instance.createDescriptors();
		instance.createProvider();
		return instance;
	}
	
	
	public void createSubModel(String deviceId, String ip, DeviceProperties ... properties) {
		if(mStammdatenSubModel == null) {
			mStammdatenSubModel = new DeviceStammDatenSubModel(deviceId, ip);
			addSubModelToAas(mStammdatenSubModel);			
			
			mVerbindungsprofilSubModel = new DeviceVerbinungsProfilSubModel(properties);
			addSubModelToAas(mVerbindungsprofilSubModel);
			
			mDeviceConnectionSubModel = new DeviceConnectionSubModel(deviceId);
			addSubModelToAas(mDeviceConnectionSubModel);
		}
	}

}
