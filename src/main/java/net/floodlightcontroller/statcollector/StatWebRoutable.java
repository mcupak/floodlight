package net.floodlightcontroller.statcollector;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.floodlightcontroller.restserver.RestletRoutable;

public class StatWebRoutable implements RestletRoutable {
    @Override
    public Restlet getRestlet(Context context) {
        Router router = new Router(context);
        router.attach("/throughput/json", StatResource.class);
        return router;
    }

    @Override
    public String basePath() {
        return "/wm/stat";
    }
}
