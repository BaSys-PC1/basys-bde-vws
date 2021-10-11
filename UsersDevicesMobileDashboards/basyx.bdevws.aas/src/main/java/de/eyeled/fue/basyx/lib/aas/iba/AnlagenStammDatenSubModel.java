package de.eyeled.fue.basyx.lib.aas.iba;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class AnlagenStammDatenSubModel extends BdeSubModel {

	public static final String SUBMODEL_CORE_DATA = "Stammdaten";
	public static final String PROPERTY_ID = "AnlagenId";
	public static final String PROPERTY_NAME = "AnlagenName";
	public static final String PROPERTY_TYPE = "AnlagenTyp";
	public static final String PROPERTY_IMAGE = "AnlagenImage";
		
	private String mId;
	private String mType;
	private String mImage;
	private String mName;
	
	public AnlagenStammDatenSubModel(String id, String type, String name, String image) {
		super();
		
		mId = id;
		mType = type;
		mImage = image;
		mName = name;
		
		mUrn = new ModelUrn(
				AnlagenAssetAdministrationShell.LEGAL_ENTITY, 
				AnlagenAssetAdministrationShell.SUBUNIT, 
				SUBMODEL_CORE_DATA, "1.0", "1", 
				BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}
	
	protected void init() {
		setIdShort(SUBMODEL_CORE_DATA);
		setIdentification(IdentifierType.IRI, mUrn.getURN());

		createSubModelProperty(mId, PROPERTY_ID);
		createSubModelProperty(mName, PROPERTY_NAME);
		createSubModelProperty(mType, PROPERTY_TYPE);	
		createSubModelProperty(mImage, PROPERTY_IMAGE);		
	}
	
}

