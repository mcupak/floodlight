package net.floodlightcontroller.statcollector;

/**
 * Port statistics as displayed by the module.
 * 
 * @author mcupak
 * 
 */
public class PortStat {
	private String switchId = "";
	private Integer portNumber = 0;
	private Long receiveBytes = new Long(0);
	private Long transmitBytes = new Long(0);
	private Double bandwidth = new Double(0);
	private Long period = new Long(0);

	public PortStat() {
	}

	public String computeId() {
		return switchId + "/" + portNumber;
	}

	public Double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public String getSwitchId() {
		return switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	public Long getReceiveBytes() {
		return receiveBytes;
	}

	public void setReceiveBytes(Long receiveBytes) {
		this.receiveBytes = receiveBytes;
	}

	public Long getTransmitBytes() {
		return transmitBytes;
	}

	public void setTransmitBytes(Long transmitBytes) {
		this.transmitBytes = transmitBytes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bandwidth == null) ? 0 : bandwidth.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result
				+ ((portNumber == null) ? 0 : portNumber.hashCode());
		result = prime * result
				+ ((receiveBytes == null) ? 0 : receiveBytes.hashCode());
		result = prime * result
				+ ((switchId == null) ? 0 : switchId.hashCode());
		result = prime * result
				+ ((transmitBytes == null) ? 0 : transmitBytes.hashCode());
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
		PortStat other = (PortStat) obj;
		if (bandwidth == null) {
			if (other.bandwidth != null)
				return false;
		} else if (!bandwidth.equals(other.bandwidth))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (portNumber == null) {
			if (other.portNumber != null)
				return false;
		} else if (!portNumber.equals(other.portNumber))
			return false;
		if (receiveBytes == null) {
			if (other.receiveBytes != null)
				return false;
		} else if (!receiveBytes.equals(other.receiveBytes))
			return false;
		if (switchId == null) {
			if (other.switchId != null)
				return false;
		} else if (!switchId.equals(other.switchId))
			return false;
		if (transmitBytes == null) {
			if (other.transmitBytes != null)
				return false;
		} else if (!transmitBytes.equals(other.transmitBytes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PortStat [switchId=" + switchId + ", portNumber=" + portNumber
				+ ", receiveBytes=" + receiveBytes + ", transmitBytes="
				+ transmitBytes + ", bandwidth=" + bandwidth + ", period="
				+ period + "]";
	}

}
