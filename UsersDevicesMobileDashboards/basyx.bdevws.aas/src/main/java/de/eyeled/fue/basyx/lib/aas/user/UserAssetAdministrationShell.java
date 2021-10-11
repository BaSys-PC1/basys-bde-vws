package de.eyeled.fue.basyx.lib.aas.user;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.servlets.ImageServlet;

public class UserAssetAdministrationShell extends BdeAdmistrationShell {

	private static final String USER_AAS_TYPE_SHORT = "UserAASType";
	
	public static final String LEGAL_ENTITY = "de.eyeled";
	public static final String SUBUNIT = "basys.bdevws.user";
	public static final String SUBMODEL = "aas";
	public static final String VERSION = "1.0";
	public static final String REVISION = "1";
	public static final String INSTANCE = "001";

	private static ModelUrn sUserAasTypeUrn = new ModelUrn(LEGAL_ENTITY, SUBUNIT, SUBMODEL, VERSION, REVISION, "userAasType", INSTANCE);	
	
	private static UserAssetAdministrationShell aasType;
	
	private UserStammDatenSubModel mStammdatenSubModel;
	private UserRoleSubModel mRolesSubModel;
	private UserSkillSubModel mSkillsSubModel;
	private UserViewSubModel mViewSubModel;
	
	private UserAssetAdministrationShell() {
	}

	/**
	 * Add a servlet mapping using the id short parameter
	 * @param context
	 */
	@Override
	public void addServletMapping(BaSyxContext context) {
		super.addServletMapping(context);
		
		if(mStammdatenSubModel != null && mStammdatenSubModel.getImageData() != null) {
			HttpServlet servlet = new ImageServlet(mStammdatenSubModel.getImageData());
			context.addServletMapping(""+this.endpointMapping+"/image", servlet);
		}
	}
	
	/**
	 * Get the UserAssetAdministrationShell Type object.
	 * In Basys context type means template. In Java context we use a singleton instance.
	 * @return UserAssetAdministrationShell
	 */
	public static UserAssetAdministrationShell type() {		
		if(aasType == null) {
			aasType = new UserAssetAdministrationShell();
			aasType.setIdentification(IdentifierType.IRI, sUserAasTypeUrn.getURN());
			aasType.setIdShort(USER_AAS_TYPE_SHORT);
			aasType.setAsset(UserAsset.type());
			aasType.createDescriptors();
			aasType.createProvider();
			Reference ref = new Reference(new Key(KeyElements.ASSET, false, UserAsset.type().getIdShort(), KeyType.CUSTOM));
			aasType.setAssetReference(ref);
		}
		
		return aasType;
	}

	/**
	 * Get a UserAssetAdministrationShell Instance object. 
	 * In Basys context instance means a "real" object, like a device or user.
	 * The UserAssetAdministrationShell template (type) object will be used to set the "derivedFrom" parameter.
	 * @param idShort
	 * @param urn
	 * @param userAsset
	 * @param description
	 * @return
	 */
	public UserAssetAdministrationShell createUserAasInstance(
			String idShort, String endpoint, String endpointType, String endpointMapping,
			Asset userAsset, LangStrings description) {
		String userAasUrn = new ModelUrn(LEGAL_ENTITY, SUBUNIT, SUBMODEL, VERSION, REVISION, idShort, INSTANCE).getURN();		
		UserAssetAdministrationShell instance = new UserAssetAdministrationShell();
		instance.setIdentification(IdentifierType.IRI, userAasUrn);
		instance.setIdShort(idShort);
		instance.setDescription(description);
//		instance.setDerivedFrom(Reference.createAsFacade(this));
		instance.setAsset(userAsset);
		instance.setEndpoint(endpoint, endpointMapping+idShort, endpointType);
		instance.createDescriptors();
		instance.createProvider();
		Reference ref = new Reference(new Key(KeyElements.ASSET, false, userAsset.getIdShort(), KeyType.CUSTOM));
		instance.setAssetReference(ref);
		return instance;
	}
	
	/**
	 * Create a Stammdaten SubModel. Should only be used for AAS objects of type "Instance"
	 * @param name
	 * @param userName
	 * @param password
	 * @param emails
	 */
	public void createStammdatenSubModel(String name, String userName, String password, String userId,  String imageData, String[] emails) {
		if(mStammdatenSubModel == null) {
			if(imageData == null) {
				imageData = "";
			}
			
			String image = !imageData.isEmpty() ? this.endpoint + this.endpointMapping+"/image" : "";
			
			mStammdatenSubModel = new UserStammDatenSubModel(name, userName, password, userId, image, imageData, emails);
			addSubModelToAas(mStammdatenSubModel);
		}
	}
	
	public void createRollsSubModel(String ... roleIds) {
		if(mRolesSubModel == null) {
			mRolesSubModel = new UserRoleSubModel(roleIds);
			addSubModelToAas(mRolesSubModel);			
		}
	}

	public void createSkillsSubModel(String ... skillIds) {
		if(mSkillsSubModel == null) {
			mSkillsSubModel = new UserSkillSubModel(skillIds);			
			addSubModelToAas(mSkillsSubModel);	
		}
	}	

	public void createViewSubModel(String [][] viewData) {
		if(mViewSubModel == null) {
			mViewSubModel = new UserViewSubModel(viewData);			
			addSubModelToAas(mViewSubModel);	
		}
	}	
	
	public List<ISubmodel> getUserSubModels(){
		List<ISubmodel> submodels = new ArrayList<ISubmodel>();
		submodels.add(this.mStammdatenSubModel);
		
		if(this.mSkillsSubModel != null) {
			submodels.add(this.mSkillsSubModel);
		}
		if(this.mRolesSubModel != null) {
			submodels.add(this.mRolesSubModel);
		}
		if(this.mViewSubModel != null) {
			submodels.add(this.mViewSubModel);
		}
		return submodels;
	}
}
