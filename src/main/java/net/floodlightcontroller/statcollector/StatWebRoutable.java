
package net.floodlightcontroller.statcollector;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.floodlightcontroller.restserver.RestletRoutable;

/**
 * RestletRoutable binding Stat Collector module stats to their URLs.
 * 
 * @author mcupak
 *
 */
public class StatWebRoutable implements RestletRoutable {
    @Override
    public Restlet getRestlet(Context context) {
        Router router = new Router(context);
        router.attach(Constants.LINK_BANDWIDTH, LinkBandwidthResource.class);
        router.attach(Constants.PORT_BANDWIDTH, PortBandwidthResource.class);
        router.attach(Constants.FLOW_BANDWIDTH, FlowBandwidthResource.class);
        router.attach(Constants.SWITCH_LOAD, SwitchLoadResource.class);
        router.attach(Constants.DEVICE_ACTIVITY, DeviceActivityResource.class);
        router.attach(Constants.PROTOCOL_CLASSIFICATION, ProtocolClassifierResource.class);
        return router;
    }

    @Override
    public String basePath() {
        return Constants.URL_BASE;
    }
}
