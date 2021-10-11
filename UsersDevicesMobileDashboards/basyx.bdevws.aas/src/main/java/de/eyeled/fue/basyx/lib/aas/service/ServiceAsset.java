package de.eyeled.fue.basyx.lib.aas.service;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

public class ServiceAsset extends Asset {

	private static final String ASSET_TYPE_SHORT = "ServiceAssetType";
	private static ServiceAsset assetType;
	private static ModelUrn sAssetTypeURN = new ModelUrn("de.eyeled", "basys.bdevws.service", "asset", "1.0", "1", "serviceAssetType", "001");
	
	public static ServiceAsset type() {		
		if(assetType == null) {
			assetType = new ServiceAsset();	
			assetType.setAssetKind(AssetKind.TYPE);
			assetType.setIdentification(IdentifierType.IRI, sAssetTypeURN.getURN());
			assetType.setIdShort(ASSET_TYPE_SHORT);
		}
		
		return assetType;
	}
	
	public ServiceAsset getAssetInstance(String idShort) {
		String assetUrn = new ModelUrn("de.eyeled", "basys.bdevws.service", "asset", "1.0", "1", idShort, "001").getURN();
		ServiceAsset instance = new ServiceAsset();
		instance.setAssetKind(AssetKind.INSTANCE);
		instance.setIdentification(IdentifierType.IRI, assetUrn);
		instance.setIdShort(idShort);
		return instance;
	}
}