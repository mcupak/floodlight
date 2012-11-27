package net.floodlightcontroller.statcollector;

import java.util.Set;

import net.floodlightcontroller.core.module.IFloodlightService;

/**
 * Service interface used by the Stat Collector module.
 * 
 * @author mcupak
 *
 */
public interface StatService extends IFloodlightService {

	Set<LinkStat> getLinkStats();
	Set<PortStat> getPortStats();
	Set<FlowStat> getFlowStats();
	Set<SwitchStat> getSwitchStats();	
}