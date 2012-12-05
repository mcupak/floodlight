package net.floodlightcontroller.statcollector;

public class MnHost {

	private String src;
	private String switchId;
	private Integer inPort;

	public String getSrc() {
		return src;
	}

	public MnHost setSrc(String source) {
		src = source;
		return this;
	}

	public Integer getInPort() {
		return inPort;
	}

	public void setInPort(Integer inPort) {
		this.inPort = inPort;
	}

	public String getSwitchId() {
		return switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inPort == null) ? 0 : inPort.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
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
		MnHost other = (MnHost) obj;
		if (inPort == null) {
			if (other.inPort != null)
				return false;
		} else if (!inPort.equals(other.inPort))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		if (switchId == null) {
			if (other.switchId != null)
				return false;
		} else if (!switchId.equals(other.switchId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MnHost [src=" + src + ", switchId=" + switchId + ", inPort="
				+ inPort + "]";
	}

}
