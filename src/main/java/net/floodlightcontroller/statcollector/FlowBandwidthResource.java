package net.floodlightcontroller.statcollector;

import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class FlowBandwidthResource extends ServerResource {
    @Get("json")
    public Set<FlowStat> retrieve() {
        StatService s = (StatService)getContext().getAttributes().get(StatService.class.getCanonicalName());
        return s.getFlowStats();
    }
}
