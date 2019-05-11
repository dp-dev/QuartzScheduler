package de.studware.availability.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingResponse {
	private static final Logger logger = Logger.getLogger(PingResponse.class.getSimpleName());
	private final String url;
	private final int avgTime;

	public PingResponse(String url, InputStream stream) {
		this.url = url;
		this.avgTime = parseStream(stream);
	}

	private int parseStream(InputStream stream) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.contains("Minimum =")) {
					return extractAverageTime(line);
				}
			}			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while parsing InputStream", e);
		}
		return 4000;
	}

	private int extractAverageTime(String line) {
		line = line.trim().replaceAll(".* ", "");
		line = line.replaceAll("ms", "");
		return Integer.parseInt(line);
	}

	public String getUrl() {
		return url;
	}

	public int getAvgTime() {
		return avgTime;
	}
	
	@Override
	public String toString() {
		return String.format("PingResponse [average response time: %4sms | url: %s]", avgTime, url);
	}

}
