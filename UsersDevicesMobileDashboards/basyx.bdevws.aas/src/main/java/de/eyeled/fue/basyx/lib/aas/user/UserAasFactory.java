package de.eyeled.fue.basyx.lib.aas.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangString;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;

public abstract class UserAasFactory {
	
	public static List<BdeAdmistrationShell> getUserAASList(
			Object newEntity, String endpoint, String endpointType, String endpointMapping){
		List<BdeAdmistrationShell> userAasList = null;
		if(newEntity instanceof HashMap<?,?>) {	
			try {
				ArrayList<Map> userList = (ArrayList<Map>) ((HashMap) newEntity).get("Benutzer");
				if(userList.size() > 0) {
					userAasList = new ArrayList<>();
					for(Map userData : userList) {	
						UserAssetAdministrationShell userAas = 
								UserAasFactory
								.createInstanceFromMap(userData, endpoint, endpointType, endpointMapping);
						if(userAas != null) {
							userAasList.add(userAas);			
						}
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				System.err.println("getUserAASList error: "+e.getMessage());
			}
		}
		
		return userAasList;
	}
	
	/**
	 * 
	 * @param userData
	 * @param endpoint
	 * @param endpointType
	 * @param endpointMapping
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static UserAssetAdministrationShell createInstanceFromMap(Map<?,?> userData, 
			String endpoint, String endpointType, String endpointMapping) {		
		try {
			Map stammdaten = (Map) userData.get("Stammdaten");
			String imageData = userData.get("Image") != null ? ((String) userData.get("Image")).replaceAll("\\s","") : null;
			String id = (String) stammdaten.get("id");
			String benutzername = (String) stammdaten.get("Benutzername");
			String passwort = (String) stammdaten.get("Passwort");
			String name = (String) stammdaten.get("Name");
			ArrayList<String> emails = stammdaten.get("Emailadressen") instanceof ArrayList ? 
					(ArrayList) stammdaten.get("Emailadressen") : null;
					Map<String,String> beschreibung = (Map) stammdaten.get("Beschreibung");
			
			String [] emailList = null; 
			if(emails != null && !emails.isEmpty()) {
				emailList = new String[emails.size()];
				int i = 0;
				for(String email : emails) {
					emailList[i++] = email;
				}
			}
			LangStrings description = new LangStrings();
			
			if(beschreibung != null) {
				for(String key : beschreibung.keySet()) {
					description.add(new LangString(key, beschreibung.get(key)));					
				}
			}
			
			Map mobiles = userData.get("Mobiles") instanceof Map ? (Map) userData.get("Mobiles") : null;
			ArrayList<String> rolls = userData.get("Rollen") instanceof ArrayList ? (ArrayList) userData.get("Rollen") : null;
			ArrayList<String> skills = userData.get("Faehigkeiten") instanceof ArrayList ? (ArrayList) userData.get("Faehigkeiten") : null;
			ArrayList<Map> view = userData.get("Darstellung") instanceof ArrayList ? (ArrayList) userData.get("Darstellung") : null;
			
			String aasIdShort = id != null && !id.isEmpty() ? id : BdeAdmistrationShell.getUniqueId();
			String assetIdShort = BdeAdmistrationShell.getUniqueId();
			
			UserAssetAdministrationShell userType = UserAssetAdministrationShell.type();

			UserAssetAdministrationShell userAas = userType.createUserAasInstance(
							benutzername, 
							endpoint, endpointType, endpointMapping,
							UserAsset.type().getUserAssetInstance(assetIdShort), 
							description);	
			userAas.createStammdatenSubModel(name, benutzername, passwort, aasIdShort, imageData, emailList);
			
			if(rolls != null && rolls.size() > 0) {
				String[] list = new String[rolls.size()];
				rolls.toArray(list);
				userAas.createRollsSubModel(list);
			}
			
			if(skills != null && skills.size() > 0) {
				String[] list = new String[skills.size()];
				skills.toArray(list);
				userAas.createSkillsSubModel(list);
			}
			
			String[][] list = null;
			if(view != null && view.size() > 0) {
				list = new String[view.size()][2];
				int i = 0;
				for(Map viewData : view) {
					list[i][0] = (String) viewData.get("id");
					list[i][1] = (String) viewData.get("data");
					i++;
				}
			}
			userAas.createViewSubModel(list);
			
			return userAas;
		}
		catch (Exception e) {}
		
		return null;
	}
}
