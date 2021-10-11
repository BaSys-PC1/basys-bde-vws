package de.eyeled.fue.basyx.lib.vab;

import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaHandler;

/**
 * BdeVABLambdaHandler will be called during submodel get.
 * Could be used for post processing submodel data -> hide 
 * information like passwords.
 * @author kloppenborg
 *
 */
public class BdeVABLambdaHandler extends VABLambdaHandler {

	@Override
	public Object postprocessObject(Object element) {
		Object data = super.postprocessObject(element);
		
		return data;
	}
}
