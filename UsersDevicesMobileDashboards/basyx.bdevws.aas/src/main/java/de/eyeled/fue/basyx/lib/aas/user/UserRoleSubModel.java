package de.eyeled.fue.basyx.lib.aas.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class UserRoleSubModel extends BdeSubModel {

	
	public static final String SUBMODEL_ROLES = "Rollen";
	public static final String PROPERTY_ROLE = "Rolle";
	public static final String PROPERTY_ROLE_ID = "RollenId";
	public static final String OPERATION_ADD_ROLE = "addRolle";

	private SubmodelElementCollection mRoleContainer;
	private String[] mRoleIds;
	private HashMap<String,String> mRoleList = new HashMap<>();
	

	public UserRoleSubModel(String ... roleIds) {
		super();

		mRoleIds = roleIds;
		
		mUrn = new ModelUrn(
				UserAssetAdministrationShell.LEGAL_ENTITY, 
				UserAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_ROLES, "1.0", "1", BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}

	protected void init() {
		setIdShort(SUBMODEL_ROLES);
		setIdentification(IdentifierType.IRI, mUrn.getURN());
		mRoleContainer = new SubmodelElementCollection();
		mRoleContainer.setIdShort(PROPERTY_ROLE);
		
		if(mRoleIds != null && mRoleIds.length > 0) {
			for(int i = 0; i < mRoleIds.length; i++) {
				addContainerProperty(mRoleContainer, mRoleList, mRoleIds[i], PROPERTY_ROLE_ID);
			}
		}

		addSubmodelElement(mRoleContainer);
		
		Property out1Property = createPropertyTemplate(ValueType.String, "roleName");
		Property out2Property = createPropertyTemplate(ValueType.Boolean, "success");
		
		OperationVariable out1 = new OperationVariable(out1Property);
		OperationVariable out2 = new OperationVariable(out2Property);
		
		Operation addOperation = new Operation(arr -> {
			Object[] response = new Object[2];
			if(arr != null && arr.length > 0 && 
					arr[0] instanceof String) {
				response[0] = (String) arr[0];
				response[1] = addContainerProperty(mRoleContainer, mRoleList, (String) arr[0], PROPERTY_ROLE_ID);
			}
			else {
				response[0] = "";
				response[1] = false;
			}
			
			out1.getValue().setValue(response[0]);
			out2.getValue().setValue(response[1]);

			return response[1];
		});
		
		

		Property in1Property = createPropertyTemplate(ValueType.String, "roleName");
		addOperation.setInputVariables(Collections.singletonList(
				new OperationVariable(in1Property)));		

		List<OperationVariable> out = new ArrayList<>();	
		out.add(out1);
		out.add(out2);		
		
		addOperation.setOutputVariables(out);	
		addOperation.setIdShort(OPERATION_ADD_ROLE);
		
		addSubmodelElement(addOperation);
	}
	
	

}
