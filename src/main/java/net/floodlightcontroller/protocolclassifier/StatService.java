package net.floodlightcontroller.protocolclassifier;

import java.util.Set;

import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.protocolclassifier.ProtocolStat;

/**
 * Service interface used by the Stat Collector module.
 * 
 * @author mcupak
 *
 */
public interface StatService extends IFloodlightService {

	Set<ProtocolStat> getProtocolStats();
}