package de.eyeled.fue.basyx.lib.aas.iba;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

public class AnlagenAsset extends Asset{
	private static final String ASSET_TYPE_SHORT = "AnlagenAssetType";
	private static AnlagenAsset assetType;
	private static ModelUrn assetTypeURN = new ModelUrn(
			AnlagenAssetAdministrationShell.LEGAL_ENTITY, 
			AnlagenAssetAdministrationShell.SUBUNIT, 
			"asset", "1.0", "1", "deviceAssetType", "001");
	
	public static AnlagenAsset type() {		
		if(assetType == null) {
			assetType = new AnlagenAsset();	
			assetType.setAssetKind(AssetKind.TYPE);
			assetType.setIdentification(IdentifierType.IRI, assetTypeURN.getURN());
			assetType.setIdShort(ASSET_TYPE_SHORT);
		}
		
		return assetType;
	}
	
	public AnlagenAsset getAssetInstance(String idShort) {
		String assetUrn = new ModelUrn(
				AnlagenAssetAdministrationShell.LEGAL_ENTITY, 
				AnlagenAssetAdministrationShell.SUBUNIT, 
				"asset", "1.0", "1", idShort, "001").getURN();
		AnlagenAsset instance = new AnlagenAsset();
		instance.setAssetKind(AssetKind.INSTANCE);
		instance.setIdentification(IdentifierType.IRI, assetUrn);
		instance.setIdShort(idShort);
		return instance;
	}

}

