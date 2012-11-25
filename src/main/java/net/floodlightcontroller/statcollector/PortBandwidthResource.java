package net.floodlightcontroller.statcollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.floodlightcontroller.core.types.SwitchMessagePair;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class PortBandwidthResource extends ServerResource {
    @Get("json")
    public Set<PortStat> retrieve() {
        StatService s = (StatService)getContext().getAttributes().get(StatService.class.getCanonicalName());
        return s.getPortStats();
    }
}
