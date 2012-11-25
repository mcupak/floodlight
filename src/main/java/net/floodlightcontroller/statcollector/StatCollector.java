package net.floodlightcontroller.statcollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFType;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.core.types.SwitchMessagePair;
import net.floodlightcontroller.restserver.IRestApiService;

/**
 * Collector of throughput/bandwidth/link-related stats.
 * 
 * @author mcupak
 * 
 */
public class StatCollector implements IFloodlightModule, IOFMessageListener,
		StatService {

	// common
	protected IFloodlightProviderService floodlightProvider;
	protected IRestApiService restApi;
	private StatDeserializer deserializer;
	private Timer statTimer;
	// link bandwidth
	private long lastTimeLinkStat;
	private volatile Map<String, LinkStat> linkStats;
	// port bandwidth
	private long lastTimePortStat;
	private volatile Map<String, PortStat> portStats;
	// flow bandwidth
	private volatile Set<FlowStat> flowStats;

	/**
	 * Inner clas performing link bandwidth measurements.
	 * 
	 * @author mcupak
	 * 
	 */
	class LinkStatTask extends TimerTask {

		@Override
		public void run() {
			Map<String, LinkStat> links = deserializer.getLinkStats();
			Map<String, PortStat> ports = deserializer.getPortStats();
			long now = System.currentTimeMillis();
			long period = now - lastTimeLinkStat;
			lastTimeLinkStat = now;

			for (LinkStat s : links.values()) {
				s.setPeriod(period);
				// get byte difference
				PortStat port1 = ports.get(s.getSrcSwitch() + "/"
						+ s.getSrcPort());
				if (port1 == null)
					port1 = new PortStat();
				PortStat port2 = ports.get(s.getDstSwitch() + "/"
						+ s.getDstPort());
				if (port2 == null)
					port2 = new PortStat();

				PortStat pPort1 = portStats.get(s.getSrcSwitch() + "/"
						+ s.getSrcPort());
				if (pPort1 == null)
					pPort1 = new PortStat();
				PortStat pPort2 = portStats.get(s.getDstSwitch() + "/"
						+ s.getDstPort());
				if (pPort2 == null)
					pPort2 = new PortStat();

				Long byteDiff = (port1.getReceiveBytes()
						+ port1.getTransmitBytes() + port2.getReceiveBytes()
						+ port2.getTransmitBytes() - pPort1.getReceiveBytes()
						- pPort1.getTransmitBytes() - pPort2.getReceiveBytes() - pPort2
						.getTransmitBytes()) / 2;
				// set bandwidth to the average of the two ports, they should
				// have roughly the same value
				s.setBandwidth(byteDiff.doubleValue() / (period / 1000));
			}

			linkStats = links;
		}
	}

	/**
	 * Inner clas performing port bandwidth measurements.
	 * 
	 * @author mcupak
	 * 
	 */
	class PortStatTask extends TimerTask {

		@Override
		public void run() {
			Map<String, PortStat> ports = deserializer.getPortStats();
			// measure time since last measurement - executed repeatedly just to
			// be precise
			long now = System.currentTimeMillis();
			long period = now - lastTimePortStat;
			lastTimePortStat = now;

			for (PortStat s : ports.values()) {
				s.setPeriod(period);
				// get byte difference
				PortStat previous = portStats.get(s.computeId());
				if (previous == null)
					previous = new PortStat();
				Long byteDiff = (s.getReceiveBytes() + s.getTransmitBytes() - (previous
						.getReceiveBytes() + previous.getTransmitBytes()));
				s.setBandwidth(byteDiff.doubleValue() / (period / 1000));
			}

			portStats = ports;
		}

	}

	/**
	 * Inner clas performing flow bandwidth measurements.
	 * 
	 * @author mcupak
	 * 
	 */
	class FlowStatTask extends TimerTask {

		@Override
		public void run() {
			Set<FlowStat> flows = deserializer.getFlowStats();

			for (FlowStat s : flows) {
				// bytes per socond
				s.setBandwidth(s.getByteCount() / (s.getDuration() / 1000));
			}

			flowStats = flows;
		}
	}

	@Override
	public String getName() {
		return "StatCollector";
	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		return false;
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		return false;
	}

	@Override
	public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		switch (msg.getType()) {
		case PACKET_IN:

			break;
		default:
			break;
		}
		return Command.CONTINUE;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(StatService.class);
		return l;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		Map<Class<? extends IFloodlightService>, IFloodlightService> m = new HashMap<Class<? extends IFloodlightService>, IFloodlightService>();
		m.put(StatService.class, this);
		return m;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		l.add(IRestApiService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context
				.getServiceImpl(IFloodlightProviderService.class);
		restApi = context.getServiceImpl(IRestApiService.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context) {
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
		restApi.addRestletRoutable(new StatWebRoutable());
		deserializer = StatDeserializer.getInstance();

		linkStats = new HashMap<String, LinkStat>();
		portStats = new HashMap<String, PortStat>();
		flowStats = new HashSet<FlowStat>();

		statTimer = new Timer();
		// link bandwidth
		lastTimeLinkStat = System.currentTimeMillis();
		statTimer.schedule(new LinkStatTask(), 0,
				Constants.STAT_COLLECTION_INTERVAL);
		// port bandwidth
		lastTimePortStat = System.currentTimeMillis();
		statTimer.schedule(new PortStatTask(), 100,
				Constants.STAT_COLLECTION_INTERVAL);
		// flow bandwidth
		statTimer.schedule(new FlowStatTask(), 200,
				Constants.STAT_COLLECTION_INTERVAL);
	}

	public Set<LinkStat> getLinkStats() {
		return new HashSet<LinkStat>(linkStats.values());
	}

	public Set<PortStat> getPortStats() {
		return new HashSet<PortStat>(portStats.values());
	}

	public Set<FlowStat> getFlowStats() {
		return flowStats;
	}

}
