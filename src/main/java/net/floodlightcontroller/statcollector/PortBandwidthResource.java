package net.floodlightcontroller.statcollector;

import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class PortBandwidthResource extends ServerResource {
    @Get("json")
    public Set<PortStat> retrieve() {
        StatService s = (StatService)getContext().getAttributes().get(StatService.class.getCanonicalName());
        return s.getPortStats();
    }
}
