package net.floodlightcontroller.protocolclassifier;


import java.util.HashMap;
import java.util.Map;

public class ProtocolStat 
{
	private String nw_prot;
	private String nw_prot_type;
	private int no;
	private double percentage;	
	private int total;
	
	Map<String, String> map;
		
	public Map<String, String> getMap() {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("1","ICMP");map.put("2","IGMP");map.put("6","TCP");map.put("8","EGP");
		map.put("9","IGP");map.put("17","UDP");map.put("37","DDP");map.put("45","IDRP");
		map.put("46","RSVP");map.put("47","GRE");map.put("75","PVP");map.put("84","TTP");
		map.put("88","EIGRP");map.put("89","OSPF");map.put("92","MTP");map.put("94","IPIP");
		map.put("103","PIM");map.put("108","IPComp");map.put("115","L2TP");map.put("121","SMp");
		map.put("123","PTP");map.put("124","IS-IS over IPv4");map.put("139","HIP");
		return map;
	}

	public ProtocolStat() {
		super();
	}
	
	public void setMap(Map<String, String> map) {
		
		this.map = map;
	}

	public String getNw_prot_type() {
		return nw_prot_type;
	}

	public void setNw_prot_type(String nw_prot) 
	{				
		this.nw_prot_type = this.getMap().get(nw_prot);
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public double getPercentage() {
		
		return percentage;
	}

	public void setPercentage(double percentage) {
				
		this.percentage = Math.ceil((percentage/total) * 100);
	}	

	public String getNw_prot() {
		return nw_prot;
	}

	public void setNw_prot(String nw_prot) {
		this.nw_prot = nw_prot;
	}
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	

	@Override
	public String toString() {
		return " Protocol Number" + ", Protocol Type: " + nw_prot_type
				+ ", Used in Network: "+percentage + " %, Flows Using " + nw_prot_type + " Protocol: " + no + ", Total Number of Flows : " + total;
	}

}
