package net.floodlightcontroller.statcollector;

/**
 * Device statistics as displayed by the module.
 * 
 * @author mcupak
 * 
 */
public class DeviceStat {
	private String address = "";
	private Long period = new Long(0);
	private Long receiveBytes = new Long(0);
	private Long transmitBytes = new Long(0);
	private Double activity = new Double(0);

	public DeviceStat() {
		super();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
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

	public Double getActivity() {
		return activity;
	}

	public void setActivity(Double activity) {
		this.activity = activity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activity == null) ? 0 : activity.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result
				+ ((receiveBytes == null) ? 0 : receiveBytes.hashCode());
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
		DeviceStat other = (DeviceStat) obj;
		if (activity == null) {
			if (other.activity != null)
				return false;
		} else if (!activity.equals(other.activity))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (receiveBytes == null) {
			if (other.receiveBytes != null)
				return false;
		} else if (!receiveBytes.equals(other.receiveBytes))
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
		return "DeviceStat [address=" + address + ", period=" + period
				+ ", receiveBytes=" + receiveBytes + ", transmitBytes="
				+ transmitBytes + ", activity=" + activity + "]";
	}

}
