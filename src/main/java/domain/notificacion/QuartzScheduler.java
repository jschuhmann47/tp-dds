package domain.notificacion;


import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


public class QuartzScheduler {

    public static void main(String[] args) throws SchedulerException {,
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        JobDetail job = newJob(Ejecutor.class)
                .withIdentity("myJob","group1")
                .build();

       SimpleTrigger trigger = newTrigger().withIdentity("myTrigger","group1")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInMinutes(5).repeatForever())//withIntervalInMilliseconds(5).repeatForever())
                .build();

        scheduler.scheduleJob(job,trigger);


    }
    }