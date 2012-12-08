package net.floodlightcontroller.protocolclassifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.restserver.IRestApiService;
import net.floodlightcontroller.statcollector.MnHost;

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

		// Instantiate two objects for OFMatch and OFPacketIn
		OFPacketIn pin = (OFPacketIn) msg;
		OFMatch match = new OFMatch();

		Map<String, String> map = new HashMap<String, String>();
		map.put("1","ICMP");map.put("2","IGMP");map.put("6","TCP");map.put("8","EGP");
		map.put("9","IGP");map.put("17","UDP");map.put("37","DDP");map.put("45","IDRP");
		map.put("46","RSVP");map.put("47","GRE");map.put("75","PVP");map.put("84","TTP");
		map.put("88","EIGRP");map.put("89","OSPF");map.put("92","MTP");map.put("94","IPIP");
		map.put("103","PIM");map.put("108","IPComp");map.put("115","L2TP");map.put("121","SMp");
		map.put("123","PTP");map.put("124","IS-IS over IPv4");map.put("139","HIP");
		
		match.loadFromPacket(pin.getPacketData(), pin.getInPort());
		
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
		//Classifying Protocol Network

		if(map.containsKey(nw_prtcl))
		{
			System.out.println(map.get(nw_prtcl));
		}
		

		// Here we print the entire packet-in array which has all matchable
		// fields
		System.out.println("$$$$$-PacketIn ARRAY-$$$$$");
		System.out.println(Arrays.asList(match));

		return Command.CONTINUE;
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
	
	public Set<ProtocolStat> getProtocolStats() {
		// TODO Auto-generated method stub
		return null;
	}

}
