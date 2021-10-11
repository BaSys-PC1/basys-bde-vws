package de.eyeled.fue.basyx.lib.aas.user;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;

public class UserConfigAas extends BdeAdmistrationShell {

	private static final String USER_CONFIG_AAS_TYPE_SHORT = "UserConfigAASType";
	private static ModelUrn sAasTypeUrn = new ModelUrn(
			UserAssetAdministrationShell.LEGAL_ENTITY, UserAssetAdministrationShell.SUBUNIT, "aas", "1.0", "1", "userConfigAas", "001");	
	
	private static UserConfigAas aasType;
	
	/**
	 * 
	 * Get the UserConfigAas Type object.
	 * In Basys context type means template. In Java context we use a singleton instance.
	 * @return UserConfigAas
	 */
	public static UserConfigAas type() {		
		if(aasType == null) {
			aasType = new UserConfigAas();
			aasType.setIdentification(IdentifierType.IRI, sAasTypeUrn.getURN());
			aasType.setIdShort(USER_CONFIG_AAS_TYPE_SHORT);
			aasType.createDescriptors();
			aasType.createProvider();
		}
		
		return aasType;
	}

}
