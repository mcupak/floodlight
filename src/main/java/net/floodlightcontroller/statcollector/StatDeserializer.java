package net.floodlightcontroller.statcollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import sun.nio.cs.Surrogate;

/**
 * Deserializer of data obtained via REST API.
 * 
 * Singleton.
 * 
 * @author mcupak
 * 
 */
public class StatDeserializer {
	private static StatDeserializer instance = null;
	private URL linkStat;
	private URL portStat;

	protected StatDeserializer() {
		try {
			linkStat = new URL(Constants.URL_PROTOCOL + Constants.URL_IP
					+ Constants.URL_PORT + Constants.URL_LINKS);
			portStat = new URL(Constants.URL_PROTOCOL + Constants.URL_IP
					+ Constants.URL_PORT + Constants.URL_PORTS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static StatDeserializer getInstance() {
		if (instance == null) {
			instance = new StatDeserializer();
		}
		return instance;
	}

	public Map<String, PortStat> getPortStats() {
		Map<String, PortStat> stats = new HashMap<String, PortStat>();

		ObjectMapper m = new ObjectMapper();
		try {
			JsonNode root = m.readTree(new BufferedReader(
					new InputStreamReader(portStat.openStream())));

			Iterator<String> it = root.getFieldNames();
			String swId;
			for (JsonNode sw : root) {
				swId = it.next();
				for (JsonNode port : sw) {
					PortStat currentStat = new PortStat();
					currentStat.setSwitchId(swId);
					currentStat.setPortNumber(port.get("portNumber")
							.getValueAsInt());
					currentStat.setReceiveBytes(port.get("receiveBytes")
							.getValueAsLong());
					currentStat.setTransmitBytes(port.get("transmitBytes")
							.getValueAsLong());
					stats.put(swId + "/" + currentStat.getPortNumber(),
							currentStat);
				}
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stats;
	}

	public Set<LinkStat> getLinkStats() {
		Set<LinkStat> stats = new HashSet<LinkStat>();
		ObjectMapper m = new ObjectMapper();
		try {
			JsonNode root = m.readTree(new BufferedReader(
					new InputStreamReader(linkStat.openStream())));
			for (JsonNode link : root) {
				LinkStat currentStat = new LinkStat();
				currentStat.setSrcSwitch(link.get("src-switch")
						.getValueAsText());
				currentStat.setSrcPort(link.get("src-port").getValueAsInt());
				currentStat.setDstSwitch(link.get("dst-switch")
						.getValueAsText());
				currentStat.setDstPort(link.get("dst-port").getValueAsInt());
				stats.add(currentStat);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stats;
	}
}
