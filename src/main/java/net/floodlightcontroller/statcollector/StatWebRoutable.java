package net.floodlightcontroller.statcollector;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.floodlightcontroller.restserver.RestletRoutable;

public class StatWebRoutable implements RestletRoutable {
    @Override
    public Restlet getRestlet(Context context) {
        Router router = new Router(context);
        router.attach(Constants.LINK_BANDWIDTH, LinkBandwidthResource.class);
        router.attach(Constants.PORT_BANDWIDTH, PortBandwidthResource.class);
        router.attach(Constants.FLOW_BANDWIDTH, FlowBandwidthResource.class);
        router.attach(Constants.SWITCH_LOAD, SwitchLoadResource.class);
        return router;
    }

    @Override
    public String basePath() {
        return Constants.URL_BASE;
    }
}
