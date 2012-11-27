package net.floodlightcontroller.statcollector;

/**
 * Switch statistics as displayed by the module.
 * 
 * @author mcupak
 * 
 */
public class SwitchStat {
	private String switchId = "";
	private Long receiveBytes = new Long(0);
	private Long transmitBytes = new Long(0);
	private Double load = new Double(0);
	private Long period = new Long(0);

	public SwitchStat() {
	}

	public String getSwitchId() {
		return switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
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

	public Double getLoad() {
		return load;
	}

	public void setLoad(Double load) {
		this.load = load;
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
				+ ((switchId == null) ? 0 : switchId.hashCode());
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
		SwitchStat other = (SwitchStat) obj;
		if (switchId == null) {
			if (other.switchId != null)
				return false;
		} else if (!switchId.equals(other.switchId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SwitchStat [switchId=" + switchId + ", receiveBytes="
				+ receiveBytes + ", transmitBytes=" + transmitBytes + ", load="
				+ load + ", period=" + period + "]";
	}

}
