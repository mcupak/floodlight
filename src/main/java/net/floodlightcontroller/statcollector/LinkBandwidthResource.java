package net.floodlightcontroller.statcollector;

import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Link bandwidth resource displayed via REST.
 * 
 * @author mcupak
 *
 */
public class LinkBandwidthResource extends ServerResource {
    @Get("json")
    public Set<LinkStat> retrieve() {
        StatService s = (StatService)getContext().getAttributes().get(StatService.class.getCanonicalName());
        return s.getLinkStats();
    }
}
