package de.studware.availability;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.studware.availability.data.PingResponse;

@DisallowConcurrentExecution
public class PingExecutor implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ArrayList<String> urlList = new ArrayList<>();
		urlList.add("www.google.com");
		urlList.add("www.sap.com");
		urlList.add("www.tagesschau.de");
		System.out.println("------------------------");
		System.out.println("Execution at " + getDateTime());
		for (String url : urlList) {
			PingResponse response = pingUrl(url);
			if (response != null) {
				System.out.println(response.toString());
			}
		}
	}

	private PingResponse pingUrl(String url) {
		try {
			Process process = java.lang.Runtime.getRuntime().exec("ping -n 5 -w 4000 " + url);
			InputStream stream = process.getInputStream();
			return new PingResponse(url, stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

}
