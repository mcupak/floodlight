package net.floodlightcontroller.protocolclassifier;

import java.util.Set;

import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.protocolclassifier.ProtocolStat;

/**
 * Service interface used by the Protocol Classifier module.
 * 
 * @author mcupak
 * 
 */
public interface ProtocolService extends IFloodlightService {

	Set<ProtocolStat> getProtocolStats();
}