package net.floodlightcontroller.protocolclassifier;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.floodlightcontroller.protocolclassifier.ProtocolClassifierResource;
import net.floodlightcontroller.restserver.RestletRoutable;

/**
 * RestletRoutable binding Protocol Classifier module stats to their URLs.
 * 
 * @author mcupak
 * 
 */
public class ProtocolWebRoutable implements RestletRoutable {

	public static final String PROTOCOL_CLASSIFICATION = "/protocol/json";
	public static final String URL_BASE = "/wm/protocol";

	@Override
	public Restlet getRestlet(Context context) {
		Router router = new Router(context);
		router.attach(PROTOCOL_CLASSIFICATION, ProtocolClassifierResource.class);
		return router;
	}

	@Override
	public String basePath() {
		return URL_BASE;
	}
}
