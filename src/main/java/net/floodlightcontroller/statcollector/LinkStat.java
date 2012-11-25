package net.floodlightcontroller.statcollector;

/**
 * Representation of statistics related to a link.
 * 
 * @author mcupak
 * 
 */
public class LinkStat {

	private String srcSwitch = "";
	private String dstSwitch = "";
	private Integer srcPort = 0;
	private Integer dstPort = 0;
	private Double bandwidth = new Double(0);
	private Long period = new Long(0);

	public LinkStat() {
	}

	public String getId() {
		return getSrcSwitch() + "/" + getSrcPort() + "-" + getDstSwitch() + "/"
				+ getDstPort();
	}

	public Double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getSrcSwitch() {
		return srcSwitch;
	}

	public void setSrcSwitch(String srcSwitch) {
		this.srcSwitch = srcSwitch;
	}

	public String getDstSwitch() {
		return dstSwitch;
	}

	public void setDstSwitch(String dstSwitch) {
		this.dstSwitch = dstSwitch;
	}

	public Integer getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(Integer srcPort) {
		this.srcPort = srcPort;
	}

	public Integer getDstPort() {
		return dstPort;
	}

	public void setDstPort(Integer dstPort) {
		this.dstPort = dstPort;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bandwidth == null) ? 0 : bandwidth.hashCode());
		result = prime * result + ((dstPort == null) ? 0 : dstPort.hashCode());
		result = prime * result
				+ ((dstSwitch == null) ? 0 : dstSwitch.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((srcPort == null) ? 0 : srcPort.hashCode());
		result = prime * result
				+ ((srcSwitch == null) ? 0 : srcSwitch.hashCode());
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
		LinkStat other = (LinkStat) obj;
		if (bandwidth == null) {
			if (other.bandwidth != null)
				return false;
		} else if (!bandwidth.equals(other.bandwidth))
			return false;
		if (dstPort == null) {
			if (other.dstPort != null)
				return false;
		} else if (!dstPort.equals(other.dstPort))
			return false;
		if (dstSwitch == null) {
			if (other.dstSwitch != null)
				return false;
		} else if (!dstSwitch.equals(other.dstSwitch))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (srcPort == null) {
			if (other.srcPort != null)
				return false;
		} else if (!srcPort.equals(other.srcPort))
			return false;
		if (srcSwitch == null) {
			if (other.srcSwitch != null)
				return false;
		} else if (!srcSwitch.equals(other.srcSwitch))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LinkStat [srcSwitch=" + srcSwitch + ", dstSwitch=" + dstSwitch
				+ ", srcPort=" + srcPort + ", dstPort=" + dstPort
				+ ", bandwidth=" + bandwidth + ", period=" + period + "]";
	}

}
