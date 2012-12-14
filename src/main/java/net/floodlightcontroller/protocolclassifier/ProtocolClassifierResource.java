package net.floodlightcontroller.protocolclassifier;

import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ProtocolClassifierResource extends ServerResource {
	@Get("json")
	public Set<ProtocolStat> retrieve() {
		ProtocolService s = (ProtocolService) getContext().getAttributes().get(
				ProtocolService.class.getCanonicalName());
		return s.getProtocolStats();
	}
}
