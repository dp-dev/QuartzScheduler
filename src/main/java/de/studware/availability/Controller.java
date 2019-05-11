package de.studware.availability;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Controller {

	public static void main(String[] args) throws SchedulerException {
		JobDetail job = JobBuilder.newJob(PingExecutor.class).withIdentity("MY JOB1", "group1").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever()).build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);

	}

}
