package de.eyeled.fue.basyx.lib.aas.user;

import java.util.Collections;
import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class UserSkillSubModel extends BdeSubModel {

	public static final String SUBMODEL_SKILLS = "Faehigkeiten";
	public static final String PROPERTY_SKILL = "Faehigkeit";
	public static final String PROPERTY_SKILL_ID = "FaehigkeitId";
	public static final String OPERATION_ADD_SKILL = "addFaehigkeit";

	private SubmodelElementCollection mSkillsContainer;
	private HashMap<String,String> mSkillsList = new HashMap<>();
	
	private String[] mSkillIds;
	
	public UserSkillSubModel(String ... skillIds) {
		super();
		mSkillIds = skillIds;
		
		mUrn = new ModelUrn(UserAssetAdministrationShell.LEGAL_ENTITY, UserAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_SKILLS, "1.0", "1", BdeAdmistrationShell.getUniqueId(), "001");
		init();
	}

	protected void init() {
		setIdShort(SUBMODEL_SKILLS);
		setIdentification(IdentifierType.IRI, mUrn.getURN());
		
		mSkillsContainer = new SubmodelElementCollection();
		mSkillsContainer.setIdShort(PROPERTY_SKILL);
		
		if(mSkillIds != null && mSkillIds.length > 0) {
			for(int i = 0; i < mSkillIds.length; i++) {
				addContainerProperty(mSkillsContainer, mSkillsList, mSkillIds[i], PROPERTY_SKILL_ID);
			}
		}		
		addSubmodelElement(mSkillsContainer);
		
		Operation addOperation = new Operation(arr -> {
			if(arr != null && arr.length > 0 && 
					arr[0] instanceof String) {
				return addContainerProperty(mSkillsContainer, mSkillsList, (String) arr[0], PROPERTY_SKILL_ID);
			}
			
			return false;
		});
		addOperation.setInputVariables(Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.String, "skillName"))));
		addOperation.setOutputVariables(Collections.singletonList(
				new OperationVariable(createPropertyTemplate(ValueType.Boolean, "success"))));	
		addOperation.setIdShort(OPERATION_ADD_SKILL);
		addSubmodelElement(addOperation);
	}
}
