package de.eyeled.fue.basyx.lib.aas.iba;

import java.util.ArrayList;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.device.DeviceAsset;
import de.eyeled.fue.basyx.lib.aas.iba.data.DocumentData;
import de.eyeled.fue.basyx.lib.aas.iba.data.SensorData;

public class AnlagenAssetAdministrationShell extends BdeAdmistrationShell {

	private static final String AAS_TYPE_SHORT = "anlagenAasType";
	
	public static final String LEGAL_ENTITY = "com.iba-ag";
	public static final String SUBUNIT = "basys.bdevws.anlage";
	
	private static ModelUrn sAasTypeUrn = new ModelUrn(LEGAL_ENTITY, SUBUNIT, "aas", "1.0", "1", AAS_TYPE_SHORT, "001");	
	
	private static AnlagenAssetAdministrationShell sAasType;
	
	private ArrayList<AnlagenSensorSubModel> mSensorSubModels = new ArrayList<>();	
	private ArrayList<AnlagenDocumentsSubModel> mDocumentsSubModels = new ArrayList<>();
	private AnlagenStammDatenSubModel mStammDatenSubModel;	
	
	private AnlagenAssetAdministrationShell() {}

	public static AnlagenAssetAdministrationShell type() {		
		if(sAasType == null) {
			sAasType = new AnlagenAssetAdministrationShell();
			sAasType.setIdentification(IdentifierType.IRI, sAasTypeUrn.getURN());
			sAasType.setIdShort(AAS_TYPE_SHORT);
			sAasType.setAsset(DeviceAsset.type());
			sAasType.createDescriptors();
			sAasType.createProvider();
		}
		
		return sAasType;
	}
	

	public AnlagenAssetAdministrationShell createAasInstance(
			String idShort, String endpoint, String endpointType, String endpointMapping,
			Asset asset) {
		String aasUrn = new ModelUrn(LEGAL_ENTITY, SUBUNIT, "aas", "1.0", "1", idShort, "001").getURN();		
		AnlagenAssetAdministrationShell instance = new AnlagenAssetAdministrationShell();
		instance.setIdentification(IdentifierType.IRI, aasUrn);
		instance.setIdShort(idShort);
//		instance.setDerivedFrom(Reference.createAsFacade(this));
		instance.setAsset(asset);
		instance.setEndpoint(endpoint, endpointMapping+idShort, endpointType);
		instance.createDescriptors();
		instance.createProvider();
		return instance;
	}
	
	
	public void createStammdatenSubModel(String id, String typ, String name, String image) {
		if(mStammDatenSubModel == null) {
			mStammDatenSubModel = new AnlagenStammDatenSubModel(id, typ, name, image);
			addSubModelToAas(mStammDatenSubModel);			
		}
	}

	
	
	public void createSensorSubModel(SensorData ... sensors) {		
		if(sensors != null) {
			int i = 17;
			for(SensorData sensor : sensors) {
				sensor.setRessourceId("2:"+String.valueOf(i++));
				AnlagenSensorSubModel subModel = new AnlagenSensorSubModel(sensor);
				mSensorSubModels.add(subModel);
				addSubModelToAas(subModel);						
			}
		}
	}

	
	
	public void createDocumentsSubModel(DocumentData ... data) {
		if(data != null) {
			for(DocumentData item : data) {
				AnlagenDocumentsSubModel subModel = new AnlagenDocumentsSubModel(item);
				mDocumentsSubModels.add(subModel);
				addSubModelToAas(subModel);						
			}
		}
	}
	

}
