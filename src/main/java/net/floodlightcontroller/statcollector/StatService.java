package net.floodlightcontroller.statcollector;

import java.util.Set;

import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.core.types.SwitchMessagePair;

public interface StatService extends IFloodlightService {

	Set<LinkStat> getLinkStats();
	Set<PortStat> getPortStats();
	Set<FlowStat> getFlowStats();
	Set<SwitchStat> getSwitchStats();	
}