package de.eyeled.fue.basyx.lib.aas.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class UserViewSubModel extends BdeSubModel{

	public static final String SUBMODEL_VIEW = "Darstellung";
	public static final String PROPERTY_UI_CONFIG = "UIKonfig";
	public static final String OPERATION_ADD_UI_CONFIG = "addUIKonfig";
	public static final String OP_ADD_UI_CONFIG_ID = "id";
	public static final String OP_ADD_UI_CONFIG_DATA = "data";

	private SubmodelElementCollection mViewContainer;
	
	private String [][] mViewData;

	public UserViewSubModel(String[][] viewData) {
		super();
		this.mViewData = viewData;
		mUrn = new ModelUrn(UserAssetAdministrationShell.LEGAL_ENTITY, UserAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_VIEW, "1.0", "1", BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	protected void init() {
		setIdShort(SUBMODEL_VIEW);
		setIdentification(IdentifierType.IRI, mUrn.getURN());
		
		mViewContainer = new SubmodelElementCollection();
		mViewContainer.setIdShort(PROPERTY_UI_CONFIG);
		
		if(mViewData != null && mViewData.length > 0) {
			for(int i = 0; i < mViewData.length; i++) {
				addViewProperty(mViewData[i][0],mViewData[i][1]);
			}
		}
		
		addSubmodelElement(mViewContainer);
		
		Operation addOperation = new Operation(arr -> {
			if(arr != null && arr.length > 1 && 
					arr[0] instanceof String && 
					arr[1] instanceof String) {
				return addViewProperty((String) arr[0], (String) arr[1]);
			}
			
			return false;
		});
		List<OperationVariable> in = new ArrayList<>();				

		in.add(new OperationVariable(createPropertyTemplate(ValueType.String,"viewId")));
		in.add(new OperationVariable(createPropertyTemplate(ValueType.String,"data")));		
		
		addOperation.setInputVariables(in);
		addOperation.setOutputVariables(Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.Boolean,"success"))));	
		addOperation.setIdShort(OPERATION_ADD_UI_CONFIG);
		addSubmodelElement(addOperation);
	}
	

	
	private boolean addViewProperty(String propertyId, String data) {
		if(mViewContainer != null) {
			ISubmodelElement sme = getViewProperty(propertyId);			
			if(sme == null) {
				Property property = new Property(data);
				property.setIdShort(propertyId);					
				mViewContainer.addSubmodelElement(property);
			}
			else {
				Property property = (Property) sme;
				property.setValue(data);
				
			}
			return true;
		}
		
		return false;
	}
	
	private ISubmodelElement getViewProperty(String propertyId) {
		Collection<ISubmodelElement> currentList = mViewContainer.getValue();
		if(currentList != null && currentList.size() > 0) {
			for(ISubmodelElement sme : currentList) {
				if(propertyId.compareTo(sme.getIdShort()) == 0) {
					return sme;
				}
			}
		}
		
		return null;
		
	}
	
	
}
