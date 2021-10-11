package de.eyeled.fue.basyx.lib.vab;

import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.generic.VABModelProvider;

public class BdeVABLambdaProvider extends VABModelProvider {
	public BdeVABLambdaProvider(Map<String, Object> elements) {
		super(elements, new BdeVABLambdaHandler());
	}
}
