package net.floodlightcontroller.protocolclassifier;

import java.util.Set;

import net.floodlightcontroller.statcollector.StatService;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ProtocolClassifierResource extends ServerResource {
	    @Get("json")
	    public Set<ProtocolStat> retrieve() {
	        ProtocolService s = (ProtocolService)getContext().getAttributes().get(StatService.class.getCanonicalName());
	        return s.getProtocolStats();
	    }

}
