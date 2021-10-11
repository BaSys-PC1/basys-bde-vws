package de.eyeled.fue.basyx.lib.aas.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangString;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;


import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.device.DeviceVerbinungsProfilSubModel.DeviceProperties;

public abstract class DeviceAasFactory {
	
	@SuppressWarnings("unchecked")
	public static List<BdeAdmistrationShell> createDeviceAas(
			Object newEntity, String endpoint, String endpointType, String endpointMapping){
		if(newEntity instanceof HashMap<?,?>) {	
			try {
				HashMap<?,?> deviceData = ((HashMap<?,?>) newEntity);
				List<BdeAdmistrationShell> aasList = null;
				
				if(deviceData.containsKey("Geraete")) {
					List<Map<Object, Object>> geraete = (List) deviceData.get("Geraete");
					if(geraete.size() > 0) {
						aasList = new ArrayList<>();
						
						for(int i = 0; i < geraete.size(); i++) {
							Map geraet = (Map) geraete.get(i);
							aasList.add(createInstanceFromMap(
									geraet, endpoint, endpointType, endpointMapping));
	
						}
					}								
				}
				else {
					aasList = new ArrayList<>();
					aasList.add(createInstanceFromMap(deviceData, endpoint, endpointType, endpointMapping));
			
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DeviceAssetAdministrationShell createInstanceFromMap(Map<?,?> data, 
			String endpoint, String endpointType, String endpointMapping) {		
		try {
			Map stammdaten = (Map) data.get("Stammdaten");			
			String id = (String) stammdaten.get("id");
			String ip = (String) stammdaten.get("ip");
			
			List<String> properties = stammdaten.get("Properties") instanceof List ? 
					(List) stammdaten.get("Properties") : null;
			Map<String,String> beschreibung = (Map) stammdaten.get("Beschreibung");
			
			DeviceProperties [] propertyList = null; 
			if(properties != null && !properties.isEmpty()) {
				propertyList = new DeviceProperties[properties.size()];
				int i = 0;
				for(String prop : properties) {
					DeviceProperties dProp = DeviceProperties.fromString(prop);
					if(dProp != null) {
						propertyList[i++] = DeviceProperties.fromString(prop);
					}
				}
			}
			
			String aasIdShort = id != null && !id.isEmpty()? id : BdeAdmistrationShell.getUniqueId();
			LangStrings description = new LangStrings();
			
			if(beschreibung != null) {
				for(String key : beschreibung.keySet()) {
					description.add(new LangString(key, beschreibung.get(key)));					
				}
			}
			
			DeviceAssetAdministrationShell type = DeviceAssetAdministrationShell.type();

			DeviceAssetAdministrationShell deviceAas = type.createDeviceAasInstance(
					aasIdShort, endpoint, endpointType, endpointMapping, 
					DeviceAsset.type().getAssetInstance(BdeAdmistrationShell.getUniqueId()), description);
						
			deviceAas.createSubModel(id, ip, propertyList);
			
			
			return deviceAas;
		}
		catch (Exception e) {
			System.err.println("DeviceAasFactory error: "+e.getMessage());
		}
		
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DeviceAssetAdministrationShell createInstanceFromMap(HashMap<?,?> data, 
			String endpoint, String endpointType, String endpointMapping) {		
		try {
			HashMap stammdaten = (HashMap) data.get("Stammdaten");
			String id = (String) stammdaten.get("id");
			String ip = (String) stammdaten.get("ip");
			ArrayList<String> properties = stammdaten.get("Properties") instanceof ArrayList ? 
					(ArrayList) stammdaten.get("Properties") : null;
			HashMap<String,String> beschreibung = (HashMap) stammdaten.get("Beschreibung");
			
			DeviceProperties [] propertyList = null; 
			if(properties != null && !properties.isEmpty()) {
				propertyList = new DeviceProperties[properties.size()];
				int i = 0;
				for(String prop : properties) {
					DeviceProperties dProp = DeviceProperties.fromString(prop);
					if(dProp != null) {
						propertyList[i++] = DeviceProperties.fromString(prop);
					}
				}
			}
			
			String aasIdShort = id != null && !id.isEmpty() ? id : BdeAdmistrationShell.getUniqueId();
			LangStrings description = new LangStrings();
			
			if(beschreibung != null) {
				for(String key : beschreibung.keySet()) {
					description.add(new LangString(key, beschreibung.get(key)));					
				}
			}
			
			DeviceAssetAdministrationShell type = DeviceAssetAdministrationShell.type();

			DeviceAssetAdministrationShell deviceAas = type.createDeviceAasInstance(
					aasIdShort, endpoint, endpointType, endpointMapping, 
					DeviceAsset.type().getAssetInstance(BdeAdmistrationShell.getUniqueId()), description);
						
			deviceAas.createSubModel(id, ip, propertyList);
			
			
			return deviceAas;
		}
		catch (Exception e) {}
		
		return null;
	}
}
