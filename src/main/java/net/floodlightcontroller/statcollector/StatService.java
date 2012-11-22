package net.floodlightcontroller.statcollector;

import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.core.types.SwitchMessagePair;

public interface StatService extends IFloodlightService {

	public ConcurrentCircularBuffer<SwitchMessagePair> getBuffer();

}