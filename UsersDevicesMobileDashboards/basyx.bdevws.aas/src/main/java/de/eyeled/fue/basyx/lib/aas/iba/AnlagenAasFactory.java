package de.eyeled.fue.basyx.lib.aas.iba;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.iba.data.DocumentData;
import de.eyeled.fue.basyx.lib.aas.iba.data.SensorData;

public abstract class AnlagenAasFactory {
		
	public static List<BdeAdmistrationShell> createAas(
			JsonObject jsonData, String endpoint, String endpointType, String endpointMapping){
		if(jsonData != null) {	
			try {
				List<BdeAdmistrationShell> aasList = null;
				
				if(jsonData.has("Anlagen")) {
					JsonArray anlagen = jsonData.getAsJsonArray("Anlagen");
					if(anlagen.size() > 0) {
						aasList = new ArrayList<>();
						
						for(int i = 0; i < anlagen.size(); i++) {
							JsonObject anlage = anlagen.get(i).getAsJsonObject();
							aasList.add(createInstance(
									anlage, endpoint, endpointType, endpointMapping));
	
						}
					}								
				}
				
				return aasList;
			}
			catch(Exception e) {
				e.printStackTrace();
				System.err.println("createDeviceAas error: "+e.getMessage());
			}
		}
		
		return null;
	}
	
	
	public static AnlagenAssetAdministrationShell createInstance(JsonObject anlage, 
			String endpoint, String endpointType, String endpointMapping) {		
		try {
			JsonObject stammdaten = anlage.getAsJsonObject("Stammdaten");		
			String id = stammdaten.getAsJsonPrimitive("id").getAsString();		
			String name = stammdaten.getAsJsonPrimitive("name").getAsString();		
			String anlagenTyp = stammdaten.getAsJsonPrimitive("type").getAsString();
			String image = stammdaten.getAsJsonPrimitive("image").getAsString();
			

			JsonObject states = anlage.getAsJsonObject("States");	
			String state = states.getAsJsonPrimitive("state").getAsString();		
			String error_state = states.getAsJsonPrimitive("error_state").getAsString();
			String workload = states.has("workload") ? states.getAsJsonPrimitive("workload").getAsString(): "";		

			JsonArray sensoren = anlage.getAsJsonArray("Sensoren");		
			SensorData [] sensorList = null; 
			if(sensoren != null && sensoren.size() > 0) {
				sensorList = new SensorData[sensoren.size()];
				for(int i = 0; i < sensoren.size(); i++) {
					JsonObject sensor = sensoren.get(i).getAsJsonObject();
					sensorList[i] = SensorData.create(sensor.toString());
				}
			}

			JsonArray docs = anlage.getAsJsonArray("Dokumente");		
			DocumentData [] docsList = null; 
			if(docs != null && docs.size() > 0) {
				docsList = new DocumentData[docs.size()];
				for(int i = 0; i < docs.size(); i++) {
					JsonObject doc = docs.get(i).getAsJsonObject();
					docsList[i] = DocumentData.create(doc.toString());
				}
			}
			
			String aasIdShort = id != null && !id.isEmpty()? id : BdeAdmistrationShell.getUniqueId();			
			
			AnlagenAssetAdministrationShell type = AnlagenAssetAdministrationShell.type();

			AnlagenAssetAdministrationShell aas = type.createAasInstance(
					aasIdShort, endpoint, endpointType, endpointMapping, 
					AnlagenAsset.type().getAssetInstance(BdeAdmistrationShell.getUniqueId()));

			aas.createStammdatenSubModel(id, anlagenTyp, name, image);
			aas.createSensorSubModel(sensorList);
			aas.createDocumentsSubModel(docsList);
			
			
			return aas;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("error "+e.getMessage());
		}
		
		return null;
	}
}