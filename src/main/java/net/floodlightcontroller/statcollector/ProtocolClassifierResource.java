package net.floodlightcontroller.statcollector;

import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ProtocolClassifierResource extends ServerResource {
	    @Get("json")
	    public Set<ProtocolStat> retrieve() {
	        StatService s = (StatService)getContext().getAttributes().get(StatService.class.getCanonicalName());
	        return s.getProtocolStats();
	    }

}
