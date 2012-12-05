package net.floodlightcontroller.statcollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.restserver.IRestApiService;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFType;

/**
 * Collector of throughput/bandwidth/load-related stats.
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
	// switch load
	private long lastTimeSwitchStat;
	private volatile Map<String, SwitchStat> switchStats;
	public final ArrayList<MnHost> hostArray = new ArrayList<MnHost>();
	MnHost h = null;

	public void getDeviceActivity(OFMatch match, IOFSwitch sw)
	{	
		
		//Device activity	
		//Source Ip address for each packet-in to check the most active host
		String src = IPv4.fromIPv4Address(match.getNetworkSource());

		//System.out.println("!!!" + h.getPktNum() + "!!!");
		
		long swId = sw.getId();
		Short inPort = match.getInputPort();
		
		System.out.println("$$$$$-Input Port-$$$$$");
		System.out.println(inPort);
		System.out.println("$$$$$-Network src-$$$$$");
		System.out.println(src);
		System.out.println("$$$$$-Switch DPID-$$$$$");
		System.out.println(swId);
		
		/*
		System.out.println("$$$$$-Switch ID-$$$$$$");
		System.out.println("!!!" + linkstat + "!!!");*/
		h = new MnHost();
		h.setSrc(src);
		h.setPktNum(0);
		
		if(hostArray.isEmpty())
		{
			hostArray.add(h);
		}
		else
		{	
			
			for(int i=0; i< hostArray.size(); i++)
			{
				if(hostArray.get(i).getSrc().equals(src))
				{
					h = hostArray.get(i);
					h.setPktNum(h.getPktNum() + 1);
					hostArray.set(i,h);
					break;
				}
				else
				{
					hostArray.add(h);
				}
				
			}
		}
	}
			

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
			Long period = new Long(now - lastTimeLinkStat);
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
				s.setBandwidth(byteDiff.doubleValue()
						/ (period.doubleValue() / 1000));
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
			Long period = new Long(now - lastTimePortStat);
			lastTimePortStat = now;

			for (PortStat s : ports.values()) {
				s.setPeriod(period);
				// get byte difference
				PortStat previous = portStats.get(s.computeId());
				if (previous == null)
					previous = new PortStat();
				Long byteDiff = (s.getReceiveBytes() + s.getTransmitBytes() - (previous
						.getReceiveBytes() + previous.getTransmitBytes()));
				s.setBandwidth(byteDiff.doubleValue()
						/ (period.doubleValue() / 1000));
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

	/**
	 * Inner clas performing switch load measurements.
	 * 
	 * @author mcupak
	 * 
	 */
	class SwitchStatTask extends TimerTask {

		@Override
		public void run() {
			Map<String, PortStat> ports = deserializer.getPortStats();
			// measure time since last measurement - executed repeatedly just to
			// be precise
			long now = System.currentTimeMillis();
			Long period = new Long(now - lastTimeSwitchStat);
			lastTimeSwitchStat = now;

			Map<String, SwitchStat> switches = new HashMap<String, SwitchStat>();

			for (PortStat s : ports.values()) {
				// find the switch corresponding to the port we iterate through
				SwitchStat current = switches.get(s.getSwitchId());
				if (current == null) {
					current = new SwitchStat();
					current.setSwitchId(s.getSwitchId());
					switches.put(current.getSwitchId(), current);
				}
				// update counters value using current port values
				current.setReceiveBytes(current.getReceiveBytes()
						+ s.getReceiveBytes());
				current.setTransmitBytes(current.getTransmitBytes()
						+ s.getTransmitBytes());
			}

			// compute load based on port bandwidths
			for (SwitchStat s : switches.values()) {
				s.setPeriod(period);
				SwitchStat previous = switchStats.get(s.getSwitchId());
				if (previous == null)
					previous = new SwitchStat();
				// compute byte difference
				Long receiveByteDiff = s.getReceiveBytes()
						- previous.getReceiveBytes();
				Long transmitByteDiff = s.getTransmitBytes()
						- previous.getTransmitBytes();

				s.setReceiveBytes(receiveByteDiff);
				s.setTransmitBytes(transmitByteDiff);
				s.setLoad((receiveByteDiff.doubleValue() + transmitByteDiff
						.doubleValue()) / (period.doubleValue() / 1000));
			}

			switchStats = switches;
			System.out.println(switchStats);
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
		switchStats = new HashMap<String, SwitchStat>();

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
		// switch load
		lastTimeSwitchStat = System.currentTimeMillis();
		statTimer.schedule(new SwitchStatTask(), 300,
				Constants.STAT_COLLECTION_INTERVAL * 2);
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

	@Override
	public Set<SwitchStat> getSwitchStats() {
		return new HashSet<SwitchStat>(switchStats.values());
	}
}
