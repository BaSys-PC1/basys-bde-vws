package de.eyeled.fue.basyx.lib.aas.device;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class DeviceConnectionSubModel extends BdeSubModel {	
	public static final String SUBMODEL_COMMUNICATIONS = "Verbindungen";
	public static final String OPERATION_MESSAGING_SERVICE_SUBSCRIBE = "NachrichtenDienstAnmeldung";
	public static final String OPERATION_DATA_STREAMING_SERVICE = "DataStreamingService";
	public static final String PROPERTY_DEVICE_MESSAGE_BROKER = "MessageBroker";
	public static final String PROPERTY_DEVICE_MESSAGE_BROKER_ID = "MessageBrokerId";
	
	private String mDeviceId;
		
	public DeviceConnectionSubModel(String deviceId) {
		super();
		
		mDeviceId = deviceId;
		
		mUrn = new ModelUrn(DeviceAssetAdministrationShell.LEGAL_ENTITY, DeviceAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_COMMUNICATIONS, "1.0", "1", 
				BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	protected void init() {
		setIdShort(SUBMODEL_COMMUNICATIONS);
		setIdentification(IdentifierType.IRI, mUrn.getURN());	
//		initDeviceDataStreamingService();
//		initDeviceMessageBroker();
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void initDeviceMessageBroker() {
		List<OperationVariable> in = new ArrayList<>();	
		in.add(new OperationVariable(createPropertyTemplate(ValueType.String,"data")));
		List<OperationVariable> out = Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.String,"brokerId"))) ;
		
		Operation messagingOperation = new Operation(arr -> {
			if(arr != null && arr.length > 0 && 
					arr[0] instanceof String && !((String)arr[0]).isEmpty()) {
				System.out.println("initDeviceMessageBroker "+arr[0]);
//				ConnectionServer server = null;
//				ConnectionProperty property = null;
//				String data = (String) arr[0];
//				try {
//					server = ConnectionFactory.createConnection(ConnectionType.MESSAGE_BROKER, mDeviceId, null, null);
//					property = server != null ? server.getConnectionProperty() : null;					
//					server.getDataProvider().post(data);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				String uri = property != null ? property.getConnectionUri() : "";
//				createSubModelProperty(uri, PROPERTY_DEVICE_MESSAGE_BROKER);
//				createSubModelProperty(mDeviceId, PROPERTY_DEVICE_MESSAGE_BROKER_ID);
//				
//				
//				Gson gson = new GsonBuilder().create();  	
//				return gson.toJson(property);
				return "";
			}
			
			return "{error=\"no id specified\"}";
		});
		
		messagingOperation.setInputVariables(in);
		messagingOperation.setOutputVariables(out);
		messagingOperation.setIdShort(OPERATION_MESSAGING_SERVICE_SUBSCRIBE);
		addSubmodelElement(messagingOperation);
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void initDeviceDataStreamingService() {
		List<OperationVariable> in = new ArrayList<>();	
		in.add(new OperationVariable(createPropertyTemplate(ValueType.String,"connectionId")));
		List<OperationVariable> out = Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.String,"streamingId"))) ;
		
		Operation messagingOperation = new Operation(arr -> {
			if(arr != null && arr.length > 0 && 
					arr[0] instanceof String && !((String)arr[0]).isEmpty()) {
				System.out.println("deviceDataStreamingService "+arr[0]);
//				ConnectionServer server = null;
//				ConnectionProperty property = null;
//				String connectionId = (String) arr[0];
//				try {
//					server = ConnectionFactory.createConnection(ConnectionType.STREAM, connectionId, null, null);
//					property = server != null ? server.getConnectionProperty() : null;
//				} catch (Exception e) {
//					e.printStackTrace();
//				}		
//					
//				Gson gson = new GsonBuilder().create();  	
//				return gson.toJson(property);
				
				return "";
			}
			
			return "{error=\"no id specified\"}";
		});
		
		messagingOperation.setInputVariables(in);
		messagingOperation.setOutputVariables(out);
		messagingOperation.setIdShort(OPERATION_DATA_STREAMING_SERVICE);
		addSubmodelElement(messagingOperation);
	}
}
