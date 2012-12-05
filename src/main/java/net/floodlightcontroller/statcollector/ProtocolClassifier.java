package net.floodlightcontroller.statcollector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.restserver.IRestApiService;

import org.openflow.protocol.OFMatch;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPacketIn;
import org.openflow.protocol.OFType;
import org.openflow.util.HexString;

public class ProtocolClassifier implements IFloodlightModule,
		IOFMessageListener {

	protected IFloodlightProviderService floodlightProvider;
	protected IRestApiService restApi;
	public final ArrayList<MnHost> hostArray = new ArrayList<MnHost>();
	MnHost h = null;

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

		// BasePacket pkt = (BasePacket) IFloodlightProviderService.bcStore.get(
		// cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		// Instantiate two objects for OFMatch and OFPacketIn
		OFPacketIn pin = (OFPacketIn) msg;
		OFMatch match = new OFMatch();

		match.loadFromPacket(pin.getPacketData(), pin.getInPort());

		StatCollector st = new StatCollector();

		String src = IPv4.fromIPv4Address(match.getNetworkSource());

		h = new MnHost();
		h.setSrc(src);

		if (hostArray.isEmpty()) {
			hostArray.add(h);
		} else {

			for (int i = 0; i < hostArray.size(); i++) {
				if (hostArray.get(i).getSrc().equals(src)) {
					h = hostArray.get(i);
//					h.setPktNum(h.getPktNum() + 1);
					hostArray.set(i, h);
					break;
				} else {
					hostArray.add(h);
				}

			}
		}

		// Destination IP Address for each packet-in
		System.out.println("$$$$$-Get the Desitnation IP Address-$$$$$");
		System.out.println(IPv4.fromIPv4Address(match.getNetworkDestination()));
		// Source Mac Address for each packet-in
		System.out.println("$$$$$-Mac Address Destination-$$$$$$");
		Long sourceMACHash = Ethernet.toLong(match.getDataLayerDestination());
		System.out.println(HexString.toHexString(sourceMACHash));
		// Classifying the network protocol
		System.out.println("$$$$$-Network Protocol-$$$$$$");
		String nw_prtcl = Byte.toString((match.getNetworkProtocol()));
		System.out.println(nw_prtcl);
		// Device Activity
//		System.out.println("!!!" + h.getPktNum() + "!!!");

		// Protocol Network Checking
		if (nw_prtcl.equalsIgnoreCase("1")) {
			System.out.println("ICMP, L3 Protocol");
		} else if (nw_prtcl.equalsIgnoreCase("2")) {
			System.out.println("IGMP, L3 Protocol");
		} else if (nw_prtcl.equalsIgnoreCase("45")) {
			System.out.println("IDRP, L3 Protocol");
		} else if (nw_prtcl.equalsIgnoreCase("47")) {
			System.out.println("GRE, L3 Protocol");
		} else if (nw_prtcl.equalsIgnoreCase("54")) {
			System.out.println("NHRP, L3 Protocol");
		} else if (nw_prtcl.equalsIgnoreCase("89")) {
			System.out.println("OSPF, L3 Protocol");
		} else if (nw_prtcl.equalsIgnoreCase("124")) {
			System.out.println("IS-IS, L3 Protocol");
		} else {
			System.out.println("Not Listed!");
		}

		// Here we print the entire packet-in array which has all matchable
		// fields
		System.out.println("$$$$$-PacketIn ARRAY-$$$$$");
		System.out.println(Arrays.asList(match));
		System.out.println("$$$$$-Network Destination-$$$$$");
		System.out.println(IPv4.fromIPv4Address(match.getNetworkDestination()));

		return Command.CONTINUE;
	}

	public int getActivity(ArrayList<MnHost> al) {

		for (MnHost hosts : al) {
			// print each element from ArrayList
//			System.out.println(hosts.pktNum);
		}
		return (Integer) null;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		return l;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		Map<Class<? extends IFloodlightService>, IFloodlightService> m = new HashMap<Class<? extends IFloodlightService>, IFloodlightService>();
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
	}

}
