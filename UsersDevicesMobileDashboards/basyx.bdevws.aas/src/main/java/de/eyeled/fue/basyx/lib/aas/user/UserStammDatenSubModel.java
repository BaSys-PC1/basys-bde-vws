package de.eyeled.fue.basyx.lib.aas.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class UserStammDatenSubModel extends BdeSubModel{

	public static final String SUBMODEL_CORE_DATA = "Stammdaten";
	public static final String OPERATION_CHECK_PASSWORD = "checkPassword";
	public static final String OPERATION_ADD_DEVICE = "addDevice";
	public static final String OPERATION_REMOVE_DEVICE = "removeDevice";
	public static final String PROPERTY_USER_NAME = "Benutzername";
	public static final String PROPERTY_USER_ID = "Id";
	public static final String PROPERTY_NAME = "Name";
	public static final String PROPERTY_PASSWORD = "Passwort";
	public static final String PROPERTY_IMAGE = "Image";
	public static final String PROPERTY_EMAIL_ADDRESSES = "Emailadressen";
	public static final String PROPERTY_EMAIL_ADDRESS = "Emailadresse";
	public static final String PROPERTY_DEVICES = "Mobiles";
	public static final String PROPERTY_DEVICE_ID = "MobileId";
	
	private SubmodelElementCollection mEmailContainer;
	private SubmodelElementCollection mDevicesContainer;
	
	private String mName;
	private String mUserId;
	private String mUserName;
	private String mPassword;
	private String mImage;
	private String mImageData;
	private String[] mEmails;
	private HashMap<String,String> mDeviceList = new HashMap<>();

	public UserStammDatenSubModel(String name, String userName, String password, String userId, String image, String imageData, String ... emails) {
		super();
		mName = name;
		mUserId = userId;
		mUserName = userName;
		mPassword = password;
		mEmails = emails;
		mImage = image;
		mImageData = imageData;
		
		//create ID
		mUrn = new ModelUrn(UserAssetAdministrationShell.LEGAL_ENTITY, UserAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_CORE_DATA, 
				"1.0", "1", BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	public String getImageData() {
		return this.mImageData;
	}
	
	public String getImage() {
		return this.mImage;
	}



	protected void init() {
		setIdShort(SUBMODEL_CORE_DATA);
		setIdentification(IdentifierType.IRI, mUrn.getURN());

		createSubModelProperty(mName, PROPERTY_NAME);	
		createSubModelProperty(mUserId, PROPERTY_USER_ID);
		createSubModelProperty(mUserName, PROPERTY_USER_NAME);
		createSubModelProperty(mPassword, PROPERTY_PASSWORD, true);
		if(mImage != null && !mImage.isEmpty()) {
			createSubModelProperty(mImage, PROPERTY_IMAGE);
		}
		
		mEmailContainer = new SubmodelElementCollection();
		mEmailContainer.setIdShort(PROPERTY_EMAIL_ADDRESSES);
		
		if(mEmails != null && mEmails.length > 0) {
			for(int i = 0; i < mEmails.length; i++) {
				Property emailProperty = new Property(mEmails[i]);
				emailProperty.setIdShort(PROPERTY_EMAIL_ADDRESS+(i+1));		
				mEmailContainer.addSubmodelElement(emailProperty);
			}
		}
		
		addSubmodelElement(mEmailContainer);
		
		mDevicesContainer = new SubmodelElementCollection();
		mDevicesContainer.setIdShort(PROPERTY_DEVICES);
		addSubmodelElement(mDevicesContainer);
		
		
		initRegisterDeviceOperation();
		
		
		Operation removeDeviceOperation = new Operation(arr -> {
			if(arr != null && arr.length > 0 && 
					arr[0] instanceof String) {
				return removeUserDevice((String) arr[0]);
			}
			
			return false;
		});
		removeDeviceOperation.setInputVariables(Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.String,"deviceId"))));
		removeDeviceOperation.setOutputVariables(Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.Boolean,"success"))));		
		removeDeviceOperation.setIdShort(OPERATION_REMOVE_DEVICE);
		addSubmodelElement(removeDeviceOperation);	


		List<OperationVariable> loginIn = new ArrayList<>();	
		OperationVariable userName = new OperationVariable(new Property("userName", ValueType.String));
		OperationVariable password = new OperationVariable(new Property("password", ValueType.String));
		loginIn.add(userName);
		loginIn.add(password);	
		
		List<OperationVariable> loginOut = Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.Boolean,"valid"))) ;
		
		Operation checkPasswordOperation = new Operation(arr -> {
			if(arr != null && arr.length > 1 && 
					arr[0] instanceof String && 
					arr[1] instanceof String) {				
				IProperty propertyPassword = (IProperty) getSubmodelElements().get(PROPERTY_PASSWORD);
				IProperty propertyUsername = (IProperty) getSubmodelElements().get(PROPERTY_USER_NAME);
				
				String passwordData;
				String usernameData;
				
				try {
					passwordData = (String) propertyPassword.getValue();
					usernameData = (String) propertyUsername.getValue();
					return ((String) arr[0]).compareTo(usernameData) == 0 && 
							((String) arr[1]).compareTo(passwordData) == 0; 
				} catch (Exception e) {}
			}
			
			return false;
		});		
		checkPasswordOperation.setInputVariables(loginIn);
		checkPasswordOperation.setOutputVariables(loginOut);	
		checkPasswordOperation.setIdShort(OPERATION_CHECK_PASSWORD);
		addSubmodelElement(checkPasswordOperation);
	}
	
	private void initRegisterDeviceOperation() {
		List<OperationVariable> in = new ArrayList<>();	
		in.add(new OperationVariable(createPropertyTemplate(ValueType.String,"deviceId")));
		in.add(new OperationVariable(createPropertyTemplate(ValueType.String,"optional")));		
		List<OperationVariable> out = Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.Boolean,"success"))) ;
		
		Operation registerDeviceOperation = new Operation(arr -> {
			if(arr != null && arr.length > 1 && 
					arr[0] instanceof String && 
					arr[1] instanceof String) {
				return addContainerProperty(mDevicesContainer, 
						mDeviceList, (String) arr[0], PROPERTY_DEVICE_ID);
			}
			
			return false;
		});
		registerDeviceOperation.setInputVariables(in);
		registerDeviceOperation.setOutputVariables(out);
		registerDeviceOperation.setIdShort(OPERATION_ADD_DEVICE);
		addSubmodelElement(registerDeviceOperation);
	}
	
	private boolean removeUserDevice(String deviceId) {
		// TODO
//		if(deviceId != null) {
//			String devicePropertyId = deviceList.get(deviceId);
//			if(devicePropertyId != null) {	
//
//				ContainerPropertyProvider provider = new ContainerPropertyProvider(new VABLambdaProvider(devicesContainer));
//				try {
//					provider.deleteValue(deviceId);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			return true;
//		}
		
		return false;
	}
	
}
