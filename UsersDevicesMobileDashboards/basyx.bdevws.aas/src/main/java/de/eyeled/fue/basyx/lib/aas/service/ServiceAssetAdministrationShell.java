package de.eyeled.fue.basyx.lib.aas.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.user.UserAsset;

public class ServiceAssetAdministrationShell extends BdeAdmistrationShell {

	private static final String AAS_TYPE_SHORT = "ServiceAASType";

	private static ModelUrn sAasTypeUrn = new ModelUrn("de.eyeled", "basys.bdevws.service", "aas", "1.0", "1", "serviceAasType", "001");	
	
	private static ServiceAssetAdministrationShell aasType;
	private ServiceStammdatenSubModel mStammdatenSubModel;
	
	private ServiceAssetAdministrationShell() {
	}
	
	public static ServiceAssetAdministrationShell type() {		
		if(aasType == null) {
			aasType = new ServiceAssetAdministrationShell();
			aasType.setIdentification(IdentifierType.IRI, sAasTypeUrn.getURN());
			aasType.setIdShort(AAS_TYPE_SHORT);
			aasType.setAsset(ServiceAsset.type());
			aasType.createDescriptors();
			aasType.createProvider();
			Reference ref = new Reference(new Key(KeyElements.ASSET, false, UserAsset.type().getIdShort(), KeyType.CUSTOM));
			aasType.setAssetReference(ref);
		}
		
		return aasType;
	}

	public ServiceAssetAdministrationShell createAasInstance(
			String idShort, String endpoint,String endpointType, String endpointMapping,
			Asset asset, LangStrings description) {
		String aasUrn = new ModelUrn("de.eyeled", "basys.bdevws.service", "aas", "1.0", "1", idShort, "001").getURN();		
		ServiceAssetAdministrationShell instance = new ServiceAssetAdministrationShell();
		instance.setIdentification(IdentifierType.IRI, aasUrn);
		instance.setIdShort(idShort);
		instance.setDescription(description);
//		instance.setDerivedFrom(Reference.createAsFacade(this));
		instance.setAsset(asset);
		instance.setEndpoint(endpoint, endpointMapping+idShort, endpointType);
		instance.createDescriptors();
		instance.createProvider();
		Reference ref = new Reference(new Key(KeyElements.ASSET, false, asset.getIdShort(), KeyType.CUSTOM));
		instance.setAssetReference(ref);
		return instance;
	}
	
	public void createStammdatenSubModel(String url, String type) {
		if(mStammdatenSubModel == null) {
			mStammdatenSubModel = new ServiceStammdatenSubModel(url,type);
			addSubModelToAas(mStammdatenSubModel);
		}
	}
	
	public List<ISubmodel> getBdeSubModels(){
		List<ISubmodel> submodels = new ArrayList<ISubmodel>();
		submodels.add(this.mStammdatenSubModel);
		return submodels;
	}
}