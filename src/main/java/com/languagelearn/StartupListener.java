package com.languagelearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            return;
        }

//        SchedulerFactory sf = new StdSchedulerFactory();
//        try {
//            Scheduler scheduler = sf.getScheduler();
//
//            JobDetail job = newJob(AnyJob.class)
//                    .withIdentity(AnyJob.jobName, AnyJob.groupName)
//                    .storeDurably()
//                    .build();
//
//            job.getJobDataMap().put("param", param);
//
//            Trigger trigger = newTrigger()
//                    .withIdentity(AnyJob.triggerName, AnyJob.groupName)
//                    .withSchedule(cronSchedule(AnyJob.cron))
//                    .forJob(job)
//                    .build();
//
//            scheduler.scheduleJob(job, trigger);
//
//            scheduler.start();
//        } catch (SchedulerException e) {
//            log.error(e.toString(), e);
//        }
    }
}