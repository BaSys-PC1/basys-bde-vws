package de.eyeled.fue.basyx.lib.aas.user;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

public class UserAsset extends Asset {

	public static final String LEGAL_ENTITY = "de.eyeled";
	public static final String SUBUNIT = "basys.bdevws.user";
	private static final String USER_ASSET_TYPE_SHORT = "UserAssetType";
	private static UserAsset userAssetType;
	private static ModelUrn sUserAssetTypeURN = new ModelUrn(LEGAL_ENTITY, SUBUNIT, "asset", "1.0", "1", "userAssetType", "001");
	
	public static UserAsset type() {		
		if(userAssetType == null) {
			userAssetType = new UserAsset();	
			userAssetType.setAssetKind(AssetKind.TYPE);
			userAssetType.setIdentification(IdentifierType.IRI, sUserAssetTypeURN.getURN());
			userAssetType.setIdShort(USER_ASSET_TYPE_SHORT);
		}
		
		return userAssetType;
	}
	
	public UserAsset getUserAssetInstance(String idShort) {
		String userAssetUrn = new ModelUrn(LEGAL_ENTITY, SUBUNIT, "asset", "1.0", "1", idShort, "001").getURN();
		UserAsset instance = new UserAsset();
		instance.setAssetKind(AssetKind.INSTANCE);
		instance.setIdentification(IdentifierType.IRI, userAssetUrn);
		instance.setIdShort(idShort);
		return instance;
	}
}
