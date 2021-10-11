package de.eyeled.fue.basyx.lib.aas.service;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

import de.eyeled.fue.basyx.lib.aas.BdeAdmistrationShell;
import de.eyeled.fue.basyx.lib.aas.BdeSubModel;

public class ServiceStammdatenSubModel extends BdeSubModel{

	public static final String SUBMODEL_CORE_DATA = "Stammdaten";
	public static final String PROPERTY_SERVER_URL = "ServerUrl";
	public static final String PROPERTY_SERVER_TYPE = "ServerType";
		
	private String mServerUrl;
	private String mServerType;

	public ServiceStammdatenSubModel(String url, String type) {
		super();
		mServerUrl = url;
		mServerType = type;
		
		//create ID
		mUrn = new ModelUrn("de.eyeled", 
				"basys.bdevws.service", SUBMODEL_CORE_DATA, 
				"1.0", "1", BdeAdmistrationShell.getUniqueId(), "001");
		
		init();
	}

	protected void init() {
		setIdShort(SUBMODEL_CORE_DATA);
		setIdentification(IdentifierType.IRI, mUrn.getURN());

		createSubModelProperty(mServerUrl, PROPERTY_SERVER_URL);	
		createSubModelProperty(mServerType, PROPERTY_SERVER_TYPE);
	}	
}
