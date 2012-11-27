package net.floodlightcontroller.statcollector;

/**
 * Constants used by the StatCollector module.
 * 
 * @author mcupak
 * 
 */
public final class Constants {

	/*
	 * URL constants.
	 */
	public static final String URL_PROTOCOL = "http://";
	public static final String URL_IP = "localhost";
	public static final String URL_PORT = ":8080";
	/*
	 * Output stats.
	 */
	public static final String URL_BASE = "/wm/stat";
	public static final String LINK_BANDWIDTH = "/link/json";
	public static final String PORT_BANDWIDTH = "/port/json";
	public static final String FLOW_BANDWIDTH = "/flow/json";
	public static final String SWITCH_LOAD = "/switch/json";
	public static final int STAT_COLLECTION_INTERVAL = 5000;
	/*
	 * Input stats.
	 */
	public static final String URL_LINKS = "/wm/topology/links/json";
	public static final String URL_PORTS = "/wm/core/switch/all/port/json";
	public static final String URL_FLOWS = "/wm/core/switch/all/flow/json";

}
