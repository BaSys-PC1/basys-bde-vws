package de.eyeled.fue.basyx.lib.aas.iba;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class AnlagenStateSubModel extends BdeSubModel {

	public static final String SUBMODEL_NAME = "state";
	public static final String PROPERTY_STATE = "state";
	public static final String PROPERTY_ERROR_STATE = "error_state";
	public static final String PROPERTY_WORKLOAD = "workload";
		
	private String mState;
	private String mErrorState;
	private String mWorkload;
	
	public AnlagenStateSubModel(String state, String error_state, String workload) {
		super();
		
		mState = state;
		mErrorState = error_state;
		mWorkload = workload;
		
		mUrn = new ModelUrn(
				AnlagenAssetAdministrationShell.LEGAL_ENTITY, 
				AnlagenAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_NAME, "1.0", "1", 
				BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	protected void init() {
		setIdShort(SUBMODEL_NAME);
		setIdentification(IdentifierType.IRI, mUrn.getURN());

		createSubModelProperty(mState, PROPERTY_STATE);
		createSubModelProperty(mErrorState, PROPERTY_ERROR_STATE);
		createSubModelProperty(mWorkload, PROPERTY_WORKLOAD);	
	}
	
}

