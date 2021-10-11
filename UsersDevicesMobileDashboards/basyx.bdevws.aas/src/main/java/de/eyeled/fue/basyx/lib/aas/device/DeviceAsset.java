package de.eyeled.fue.basyx.lib.aas.device;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

public class DeviceAsset extends Asset{
	private static final String DEVICE_ASSET_TYPE_SHORT = "DeviceAssetType";
	private static DeviceAsset deviceAssetType;
	private static ModelUrn deviceAssetTypeURN = new ModelUrn(
			DeviceAssetAdministrationShell.LEGAL_ENTITY, DeviceAssetAdministrationShell.SUBUNIT,
			"asset", "1.0", "1", "deviceAssetType", "001");
	
	public static DeviceAsset type() {		
		if(deviceAssetType == null) {
			deviceAssetType = new DeviceAsset();	
			deviceAssetType.setAssetKind(AssetKind.TYPE);
			deviceAssetType.setIdentification(IdentifierType.IRI, deviceAssetTypeURN.getURN());
			deviceAssetType.setIdShort(DEVICE_ASSET_TYPE_SHORT);
		}
		
		return deviceAssetType;
	}
	
	public DeviceAsset getAssetInstance(String idShort) {
		String assetUrn = new ModelUrn(
				DeviceAssetAdministrationShell.LEGAL_ENTITY, DeviceAssetAdministrationShell.SUBUNIT,
				 "asset", "1.0", "1", idShort, "001").getURN();
		DeviceAsset instance = new DeviceAsset();
		instance.setAssetKind(AssetKind.INSTANCE);
		instance.setIdentification(IdentifierType.IRI, assetUrn);
		instance.setIdShort(idShort);
		return instance;
	}

}
