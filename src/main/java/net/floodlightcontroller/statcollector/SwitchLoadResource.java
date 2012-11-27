package net.floodlightcontroller.statcollector;

import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Switch load resource displayed via REST.
 * 
 * @author mcupak
 *
 */
public class SwitchLoadResource extends ServerResource {
    @Get("json")
    public Set<SwitchStat> retrieve() {
        StatService s = (StatService)getContext().getAttributes().get(StatService.class.getCanonicalName());
        return s.getSwitchStats();
    }
}
