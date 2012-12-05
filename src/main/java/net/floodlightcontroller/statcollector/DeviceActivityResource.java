package net.floodlightcontroller.statcollector;

import java.util.Set;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Device activity resource displayed via REST.
 * 
 * @author mcupak
 *
 */
public class DeviceActivityResource extends ServerResource {
    @Get("json")
    public Set<DeviceStat> retrieve() {
        StatService s = (StatService)getContext().getAttributes().get(StatService.class.getCanonicalName());
        return s.getDeviceStats();
    }
}
