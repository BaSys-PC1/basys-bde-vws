package de.eyeled.fue.basyx.lib.aas;

import java.util.UUID;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPI;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

import de.eyeled.fue.basyx.lib.vab.BdeVABLambdaProvider;


public class BdeAdmistrationShell extends AssetAdministrationShell {

	protected static final String AAS_MAPPING = "/aas/";
	
	protected AASDescriptor descriptor;
	protected MultiSubmodelProvider fullProvider;
	protected AASModelProvider aasProvider;
	protected String aasEndpoint;
	protected String endpoint;
	protected String endpointMapping;
	
	protected void createDescriptors() {
		descriptor = new AASDescriptor(this, aasEndpoint);	
	}
	
	protected void createProvider() {
		aasProvider = new AASModelProvider(this);
		fullProvider = new MultiSubmodelProvider();
		fullProvider.setAssetAdministrationShell(aasProvider);
	}

	protected String getSubModelEndpoint(String smId) {
		return this.aasEndpoint+"submodels/"+smId;
	}
	
	/**
	 * Add a servlet mapping using the id short parameter
	 * @param context
	 */
	public void addServletMapping(BaSyxContext context) {
		HttpServlet servlet = new VABHTTPInterface<IModelProvider>(fullProvider);
		context.addServletMapping(""+this.endpointMapping+"/*", servlet);
	}

	
	public void setEndpoint(String endpoint, String enpointMapping, String endpointType) {
		if(endpoint != null) {
			this.endpoint = endpoint;
			endpoint += enpointMapping;
			endpoint += AAS_MAPPING;
//			super.setEndpoint(endpoint, endpointType);
			this.aasEndpoint = endpoint;
			this.endpointMapping = enpointMapping;
		}
	}

	protected SubmodelDescriptor addSubModelToAas(Submodel submodel) {
		SubmodelDescriptor smDescriptor = new SubmodelDescriptor(submodel, getSubModelEndpoint(submodel.getIdShort()));
		addSubmodel(submodel);		
		this.descriptor.addSubmodelDescriptor(smDescriptor);
		// extended Version of the sub model provider using the bde vab lambda provider
		// to add post processing functionality during GET
		SubmodelProvider smProvider = new SubmodelProvider(
				new VABSubmodelAPI(new BdeVABLambdaProvider(submodel)));
		this.fullProvider.addSubmodel(smProvider);
		
		return smDescriptor;
	}
	
	public boolean register(IAASRegistry registry) {
		try {	
			registry.delete(getIdentification());
		}
		catch(Exception e) {}
		
		try {	
			registry.register(descriptor);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return false;
	}
	
	public static String getUniqueId() {
		return "BDE"+UUID.randomUUID().toString().replace("-", "_");
	}
	
	public String getAasEndpoint() {
		return this.aasEndpoint;
	}
}
