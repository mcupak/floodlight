package net.floodlightcontroller.statcollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.floodlightcontroller.core.types.SwitchMessagePair;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class LinkBandwidthResource extends ServerResource {
    @Get("json")
    public Set<LinkStat> retrieve() {
        StatService s = (StatService)getContext().getAttributes().get(StatService.class.getCanonicalName());
        return s.getLinkStats();
    }
}
