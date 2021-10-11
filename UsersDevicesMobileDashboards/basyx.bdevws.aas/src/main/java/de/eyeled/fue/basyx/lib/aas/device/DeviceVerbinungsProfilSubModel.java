package de.eyeled.fue.basyx.lib.aas.device;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class DeviceVerbinungsProfilSubModel extends BdeSubModel {	
	public static final String SUBMODEL_COMMUNICATION_PROFILE = "Verbindungsprofil";
	
	private DeviceProperties[] mProperties;
	
	public enum DeviceProperties {
		PROPERTY_TEL("Telefonie"),
		PROPERTY_BLUETOOTH("Bluetooth"),
		PROPERTY_CAMERA("CAMERA"),
		PROPERTY_BLUETOOTH_LE("Bluetooth_LE"),
		PROPERTY_WIFI("Wifi"),
		PROPERTY_NFC("NFC");
		private final String mValue;
		
		DeviceProperties(String value){
			mValue = value;
		}
		
		public String getValue() {
			return mValue;
		}
		
		public static DeviceProperties fromString(String value) {
	        for (DeviceProperties prop : DeviceProperties.values()) {
	            if (prop != null && prop.getValue().equalsIgnoreCase(value)) {
	                return prop;
	            }
	        }
	        return null;
	    }
		
		
	}
	
	public DeviceVerbinungsProfilSubModel(DeviceProperties ... properties) {
		super();
		mProperties = properties;
		
		mUrn = new ModelUrn(DeviceAssetAdministrationShell.LEGAL_ENTITY, DeviceAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_COMMUNICATION_PROFILE, "1.0", "1", BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	protected void init() {
		setIdShort(SUBMODEL_COMMUNICATION_PROFILE);
		setIdentification(IdentifierType.IRI, mUrn.getURN());
		
		if(mProperties != null && mProperties.length > 0) {
			for(DeviceProperties property : mProperties) {
				try {
					createSubModelProperty(true, property.getValue());	
				}
				catch(Exception e) {
					
				}
			}
		}
	}
	
}
