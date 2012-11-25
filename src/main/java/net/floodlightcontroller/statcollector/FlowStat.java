package net.floodlightcontroller.statcollector;

/**
 * Statistics for measuring flow bandwidth.
 * 
 * @author mcupak
 * 
 */
public class FlowStat {
	private String switchId;
	private String actions;
	private Integer priority;
	private Integer idleTimeout;
	private Integer hardTimeout;
	private Double duration;
	private Integer packetCount;
	private Integer byteCount;
	private String match;
	private Integer tableId;
	private Double bandwidth;

	public FlowStat() {
	}

	public String getSwitchId() {
		return switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(Integer idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public Integer getHardTimeout() {
		return hardTimeout;
	}

	public void setHardTimeout(Integer hardTimeout) {
		this.hardTimeout = hardTimeout;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Integer getPacketCount() {
		return packetCount;
	}

	public void setPacketCount(Integer packetCount) {
		this.packetCount = packetCount;
	}

	public Integer getByteCount() {
		return byteCount;
	}

	public void setByteCount(Integer byteCount) {
		this.byteCount = byteCount;
	}

	public Double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		result = prime * result
				+ ((hardTimeout == null) ? 0 : hardTimeout.hashCode());
		result = prime * result
				+ ((idleTimeout == null) ? 0 : idleTimeout.hashCode());
		result = prime * result + ((match == null) ? 0 : match.hashCode());
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result
				+ ((switchId == null) ? 0 : switchId.hashCode());
		result = prime * result + ((tableId == null) ? 0 : tableId.hashCode());
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
		FlowStat other = (FlowStat) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (hardTimeout == null) {
			if (other.hardTimeout != null)
				return false;
		} else if (!hardTimeout.equals(other.hardTimeout))
			return false;
		if (idleTimeout == null) {
			if (other.idleTimeout != null)
				return false;
		} else if (!idleTimeout.equals(other.idleTimeout))
			return false;
		if (match == null) {
			if (other.match != null)
				return false;
		} else if (!match.equals(other.match))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (switchId == null) {
			if (other.switchId != null)
				return false;
		} else if (!switchId.equals(other.switchId))
			return false;
		if (tableId == null) {
			if (other.tableId != null)
				return false;
		} else if (!tableId.equals(other.tableId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FlowStat [switchId=" + switchId + ", actions=" + actions
				+ ", priority=" + priority + ", idleTimeout=" + idleTimeout
				+ ", hardTimeout=" + hardTimeout + ", duration=" + duration
				+ ", packetCount=" + packetCount + ", byteCount=" + byteCount
				+ ", bandwidth=" + bandwidth + ", match=" + match
				+ ", tableId=" + tableId + "]";
	}

}
