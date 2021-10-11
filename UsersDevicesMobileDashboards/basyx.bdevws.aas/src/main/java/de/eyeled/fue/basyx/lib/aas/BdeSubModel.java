package de.eyeled.fue.basyx.lib.aas;

import java.util.HashMap;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationHelper;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaHandler;

public abstract class BdeSubModel extends Submodel {
	protected ModelUrn mUrn;
	
	public BdeSubModel() {
		super();
	}

	protected boolean addContainerProperty(
			SubmodelElementCollection container, HashMap<String, String> list, 
			String data, String propertyNamePrefix) {
		if(container != null) {
			if(!list.containsKey(data)) {
				String propertyId = propertyNamePrefix+(list.size()+1);
				list.put(data,propertyId);
				Property property = new Property(data);
				property.setIdShort(propertyId);	
				container.addSubmodelElement(property);
			}
			return true;
		}
		
		return false;
	}
	
	protected Property createSubModelProperty(Object propertyValue, String propertyId) {
		if(propertyValue == null) {
			propertyValue = "";
		}
		Property property = new Property(propertyValue);
		property.setIdShort(propertyId);
		addSubmodelElement(property);
		
		return property;		
	}
	
	protected Property createSubModelProperty(Object propertyValue, String propertyId, String category) {
		Property property = new Property(propertyValue);
		property.setIdShort(propertyId);
		property.setCategory(category);
		addSubmodelElement(property);
		
		return property;		
	}
	
	/**
	 * Untested feature to hide property elements
	 * @param propertyValue
	 * @param propertyId
	 * @param hidden
	 * @return
	 */
	protected Property createSubModelProperty(Object propertyValue, String propertyId, boolean hidden) {
		Property property = createSubModelProperty(propertyValue, propertyId);
		if(hidden) {
			property.put(VABLambdaHandler.VALUE_REMOVEKEY_SUFFIX, 1);	
		}
		return property;		
	}
	
	public static Property createPropertyTemplate(ValueType type, String idShort) {
		Property propertyTemplate = OperationHelper.createPropertyTemplate(type);
		propertyTemplate.setIdShort(idShort); 
		return propertyTemplate;
	}
	
	

}
