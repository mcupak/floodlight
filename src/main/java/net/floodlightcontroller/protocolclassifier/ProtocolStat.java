package net.floodlightcontroller.protocolclassifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Statistics for measuring protocol classification.
 * 
 * @author mcupak
 * 
 */
public class ProtocolStat {
	private String nw_prot;
	private String nw_prot_type;
	private Integer no = new Integer(0);
	private Double percentage = new Double(0);
	private Integer total = new Integer(0);

	Map<String, String> map;

	public ProtocolStat() {
		map = new HashMap<String, String>();
		map.put("1", "ICMP");
		map.put("2", "IGMP");
		map.put("6", "TCP");
		map.put("8", "EGP");
		map.put("9", "IGP");
		map.put("17", "UDP");
		map.put("37", "DDP");
		map.put("45", "IDRP");
		map.put("46", "RSVP");
		map.put("47", "GRE");
		map.put("75", "PVP");
		map.put("84", "TTP");
		map.put("88", "EIGRP");
		map.put("89", "OSPF");
		map.put("92", "MTP");
		map.put("94", "IPIP");
		map.put("103", "PIM");
		map.put("108", "IPComp");
		map.put("115", "L2TP");
		map.put("121", "SMp");
		map.put("123", "PTP");
		map.put("124", "IS-IS over IPv4");
		map.put("139", "HIP");
	}

	public String getNw_prot_type() {
		return nw_prot_type;
	}

	public void setNw_prot_type(String nw_prot) {
		this.nw_prot_type = map.get(nw_prot);
	}

	public void setPercentage(double percentage) {

		this.percentage = Math.ceil((percentage / total) * 100);
	}

	public String getNw_prot() {
		return nw_prot;
	}

	public void setNw_prot(String nw_prot) {
		this.nw_prot = nw_prot;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + ((no == null) ? 0 : no.hashCode());
		result = prime * result + ((nw_prot == null) ? 0 : nw_prot.hashCode());
		result = prime * result
				+ ((nw_prot_type == null) ? 0 : nw_prot_type.hashCode());
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProtocolStat other = (ProtocolStat) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (no == null) {
			if (other.no != null)
				return false;
		} else if (!no.equals(other.no))
			return false;
		if (nw_prot == null) {
			if (other.nw_prot != null)
				return false;
		} else if (!nw_prot.equals(other.nw_prot))
			return false;
		if (nw_prot_type == null) {
			if (other.nw_prot_type != null)
				return false;
		} else if (!nw_prot_type.equals(other.nw_prot_type))
			return false;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProtocolStat [nw_prot=" + nw_prot + ", nw_prot_type="
				+ nw_prot_type + ", no=" + no + ", percentage=" + percentage
				+ ", total=" + total + "]";
	}

}
