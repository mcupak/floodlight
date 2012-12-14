package net.floodlightcontroller.protocolclassifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.restserver.IRestApiService;

import org.openflow.protocol.OFMatch;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPacketIn;
import org.openflow.protocol.OFType;

public class ProtocolClassifier implements IFloodlightModule,
		IOFMessageListener, ProtocolService {

	protected IFloodlightProviderService floodlightProvider;
	protected IRestApiService restApi;
	private Map<String, ProtocolStat> protocolStats;
	ProtocolStat prtc;
	int total;

	@Override
	public String getName() {
		return ProtocolClassifier.class.getSimpleName();

	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	// This is where we pull fields from the packet-in
	@Override
	public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		switch (msg.getType()) {
		case PACKET_IN:
			// Instantiate two objects for OFMatch and OFPacketIn
			OFPacketIn pin = (OFPacketIn) msg;
			OFMatch match = new OFMatch();

			match.loadFromPacket(pin.getPacketData(), pin.getInPort());

			String nw_prtc = Byte.toString((match.getNetworkProtocol()));

			prtc = new ProtocolStat();
			total++;

			if (protocolStats.containsKey(nw_prtc)) {
				prtc = protocolStats.get(nw_prtc);
				prtc.setNo(prtc.getNo() + 1);

			} else {
				prtc.setNw_prot(nw_prtc);
				prtc.setNw_prot_type(nw_prtc);
				prtc.setNo(0);
				prtc.setPercentage(0);
			}

			prtc.setTotal(total);
			prtc.setPercentage(prtc.getNo());
			protocolStats.put(nw_prtc, prtc);

			System.out.println(prtc);
			break;
		default:
			break;
		}

		return Command.CONTINUE;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(ProtocolService.class);
		return l;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		Map<Class<? extends IFloodlightService>, IFloodlightService> m = new HashMap<Class<? extends IFloodlightService>, IFloodlightService>();
		m.put(ProtocolService.class, this);
		return m;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		l.add(IRestApiService.class);
		return l;
	}

	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context
				.getServiceImpl(IFloodlightProviderService.class);
		restApi = context.getServiceImpl(IRestApiService.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context) {
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
		restApi.addRestletRoutable(new ProtocolWebRoutable());

		protocolStats = new HashMap<String, ProtocolStat>();
	}

	@Override
	public Set<ProtocolStat> getProtocolStats() {
		return new HashSet<ProtocolStat>(protocolStats.values());
	}
}
