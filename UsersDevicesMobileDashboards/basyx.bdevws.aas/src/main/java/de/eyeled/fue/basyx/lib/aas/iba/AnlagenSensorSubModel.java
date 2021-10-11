package de.eyeled.fue.basyx.lib.aas.iba;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

import com.google.gson.JsonObject;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;
import de.eyeled.fue.basyx.lib.aas.iba.data.SensorData;

public class AnlagenSensorSubModel extends BdeSubModel {

	public static final String SUBMODEL_NAME = "sensoren";
	public static final String OPERATION_SENSOR_RESSOURCE = "SensorenRessource";
		
	private SensorData mSensor;
	
	public AnlagenSensorSubModel(SensorData sensor) {
		super();
		
		mSensor = sensor;
		
		mUrn = new ModelUrn(
				AnlagenAssetAdministrationShell.LEGAL_ENTITY, 
				AnlagenAssetAdministrationShell.SUBUNIT+"."+SUBMODEL_NAME, 
				mSensor.getId(), "1.0", "1", 
				BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	protected void init() {
		setIdShort(mSensor.getId());
		setIdentification(IdentifierType.IRI, mUrn.getURN());		

		createSubModelProperty(mSensor.getId(), SensorData.ID);	
		createSubModelProperty(mSensor.getName(), SensorData.NAME);	
		createSubModelProperty(mSensor.getDataType(), SensorData.DATATYPE);	
		createSubModelProperty(mSensor.getMax(), SensorData.MAX);	
		createSubModelProperty(mSensor.getMin(), SensorData.MIN);	
		createSubModelProperty(mSensor.getTyp(), SensorData.TYPE);	
		createSubModelProperty(mSensor.getUnit(), SensorData.UNIT);	
		createSubModelProperty(mSensor.getRessourceId(), SensorData.RESSOURCE);			
		
		initSensorOperation();	
	}
	
	private void initSensorOperation() {
		List<OperationVariable> in = new ArrayList<>();	
		in.add(new OperationVariable(createPropertyTemplate(ValueType.String,"sensorId")));
		List<OperationVariable> out = Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.String,"success"))) ;
		
		Operation sensorOperation = new Operation(arr -> {
			JsonObject response = new JsonObject();
			if(arr != null && arr.length > 0 && 
					arr[0] instanceof String && !((String)arr[0]).isEmpty()) {
				Object resourceValue = getSubmodelElement(SensorData.RESSOURCE).getValue();
			
				String sensorId = (String) arr[0];
				
				Random random = new Random();
			    int value =  random.ints(18, 21)
			      .findFirst()
			      .getAsInt();
				
				String resourceId = "2:"+String.valueOf(value);		
				
				if(sensorId.contains("r:")) {
					resourceId = sensorId.replace("r:", "");
				}
				
				if(resourceValue != null && 
						resourceValue instanceof String &&
						!((String) resourceValue).isEmpty()
						) {
					resourceId = (String) resourceValue;
				}
				
				System.out.println("SensorOp: "+resourceId);
				
				createSubModelProperty(resourceId, SensorData.RESSOURCE);	
				
				response.addProperty("success", true);		
				response.addProperty("sensorId", sensorId);	
				response.addProperty("ressourceId", resourceId);	
			}
			
			if(!response.has("success")) {
				response.addProperty("success", false);						
			}
			
			return response.toString();
		});
		
		sensorOperation.setInputVariables(in);
		sensorOperation.setOutputVariables(out);
		sensorOperation.setIdShort(OPERATION_SENSOR_RESSOURCE);
		addSubmodelElement(sensorOperation);
	}
	
}