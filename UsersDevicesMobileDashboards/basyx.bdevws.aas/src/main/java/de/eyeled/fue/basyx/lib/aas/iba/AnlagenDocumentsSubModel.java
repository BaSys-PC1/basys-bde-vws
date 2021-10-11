package de.eyeled.fue.basyx.lib.aas.iba;



import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;
import de.eyeled.fue.basyx.lib.aas.iba.data.DocumentData;

public class AnlagenDocumentsSubModel extends BdeSubModel {

	public static final String SUBMODEL_NAME = "dokumente";
		
	private DocumentData mData;
	
	public AnlagenDocumentsSubModel(DocumentData data) {
		super();
		
		mData = data;
		
		mUrn = new ModelUrn(
				AnlagenAssetAdministrationShell.LEGAL_ENTITY, 
				AnlagenAssetAdministrationShell.SUBUNIT+"."+SUBMODEL_NAME, 
				mData.getId(), "1.0", "1", 
				BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	protected void init() {
		setIdShort(mData.getId());
		setIdentification(IdentifierType.IRI, mUrn.getURN());		

		createSubModelProperty(mData.getId(), DocumentData.ID);	
		createSubModelProperty(mData.getName(), DocumentData.NAME);	
		createSubModelProperty(mData.getUrl(), DocumentData.URL);	
	}	
}