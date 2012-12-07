package net.floodlightcontroller.statcollector;

public class ProtocolStat 
{
	private String dl_des;
	private String dl_src;
	private String nw_des;
	private String nw_src;
	private String nw_prot;
	private String nw_prot_type;
	
	
	public String getNw_prot_type() {
		return nw_prot_type;
	}

	public void setNw_prot_type(String nw_prot_type) {
		this.nw_prot_type = nw_prot_type;
	}

	public ProtocolStat() {
		super();
	}

	public String getNw_prot() {
		return nw_prot;
	}

	public void setNw_prot(String nw_prot) {
		this.nw_prot = nw_prot;
	}

	public String getDl_des() {
		return dl_des;
	}

	public void setDl_des(String dl_des) {
		this.dl_des = dl_des;
	}

	public String getNw_des() {
		return nw_des;
	}

	public void setNw_des(String nw_des) {
		this.nw_des = nw_des;
	}

	public String getNw_src() {
		return nw_src;
	}

	public void setNw_src(String nw_src) {
		this.nw_src = nw_src;
	}

	public String getDl_src() {
		return dl_src;
	}

	public void setDl_src(String dl_src) {
		this.dl_src = dl_src;
	}

	@Override
	public String toString() {
		return "Protocol Classification: [Source IP Address=" + nw_src + " Source MAC Address:" + dl_src + " Network Protocol=" + 
				nw_prot + ", Protocol:" + nw_prot_type + " Destination IP Address=" + nw_des 
				+ " Destination MAC Address:" + dl_des + "]";
	}
}
