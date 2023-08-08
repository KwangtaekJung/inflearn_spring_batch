package io.springbatch.springbatchlecture.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Map;

import static org.quartz.JobBuilder.newJob;

public abstract class JobRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        doRun(args);
    }

    protected abstract void doRun(ApplicationArguments args);

    public Trigger buildCronScheduleTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public Trigger buildSimpleScheduleTrigger(int interval, int repeatCount) {
        return TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(interval)
                        .withRepeatCount(repeatCount))
                .build();
    }

    public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).withIdentity(name, group)
                .usingJobData(jobDataMap)
                .build();
    }
}
