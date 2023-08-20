package org.daming.hoteler.service.impl;

import org.daming.hoteler.service.IQuartzService;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author daming
 * @version 2023-07-02 18:23
 **/
@Service
public class QuartzServiceImpl implements IQuartzService {
    private Scheduler scheduler;

    @Override
    public void addJob(String name, String group, String cron, String className) throws SchedulerException {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(className);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(name, group).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            throw new SchedulerException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void addJob(String name, String group, String cron, Class<? extends Job> clazz) throws SchedulerException {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(name, group).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void addJob(String name, String group, String cron, Class<? extends Job> clazz, JobDataMap jobDataMap) throws SchedulerException {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(name, group).setJobData(jobDataMap).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage(), e.getCause());
        }
    }

//    @Override
//    public JobInfo getJob(String name, String group) throws SchedulerException {
//        var triggerKey = TriggerKey.triggerKey(name, group);
//        var jobKey =  JobKey.jobKey(name, group);
//        var jobDetail = scheduler.getJobDetail(jobKey);
//        var triggers = scheduler.getTriggersOfJob(jobKey);
//        if (triggers.isEmpty()) {
//            throw new SchedulerException("no trigger");
//        }
//        var trigger = triggers.get(0);
//        var triggerState = scheduler.getTriggerState(trigger.getKey());
//        if (!(trigger instanceof CronTrigger)) {
//            throw new SchedulerException("no cronTrigger");
//        }
//        var cronTrigger = (CronTrigger)trigger;
//        return new JobInfo().setName(name).setGroup(group).setState(triggerState.name()).setCron(cronTrigger.getCronExpression()).setTimezone(cronTrigger.getTimeZone().getID()).setClassName(jobDetail.getJobClass().getName());
//
//    }

    @Override
    public boolean modifyJob(String name, String group, String cron) throws SchedulerException {
        var triggerKey = TriggerKey.triggerKey(name, group);
        var scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        var cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, cronTrigger);
        return false;
    }

    @Override
    public boolean deleteJob(String name, String group) throws SchedulerException {
        var triggerKey = TriggerKey.triggerKey(name, group);
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        var jobKey = JobKey.jobKey(name, group);
        scheduler.deleteJob(jobKey);
        return true;
    }

    @Override
    public boolean pauseJob(String name, String group) throws SchedulerException {
        var jobKey = JobKey.jobKey(name, group);
        scheduler.pauseJob(jobKey);
        return true;
    }

    @Override
    public boolean resumeJob(String name, String group) throws SchedulerException {
        var jobKey = JobKey.jobKey(name, group);
        scheduler.resumeJob(jobKey);
        return true;
    }

//    @Override
//    public List<JobInfo> listJob() throws SchedulerException {
//        var groupNames = scheduler.getJobGroupNames();
//        var jobInfos = new ArrayList<JobInfo>(groupNames.size());
//        groupNames.forEach(groupName -> {
//            try {
//                var jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
//                jobKeys.forEach(jobKey -> {
//                    try {
//                        var jobName = jobKey.getName();
//                        var jobGroup = jobKey.getGroup();
//                        var jobDetail = scheduler.getJobDetail(jobKey);
//                        var triggers = scheduler.getTriggersOfJob(jobKey);
//                        triggers.forEach(trigger -> {
//                            try {
//                                var triggerState = scheduler.getTriggerState(trigger.getKey());
//                                if (trigger instanceof CronTrigger) {
//                                    var cronTrigger = (CronTrigger)trigger;
//                                    jobInfos.add(new JobInfo().setName(jobName).setGroup(jobGroup).setState(triggerState.name()).setCron(cronTrigger.getCronExpression()).setTimezone(cronTrigger.getTimeZone().getID()).setClassName(jobDetail.getJobClass().getName()));
//                                }
//                            } catch (SchedulerException e) {
//                                // TODO add logger
//                                e.printStackTrace();
//                            }
//                        });
//                    } catch (SchedulerException e) {
//                        // TODO add logger
//                        e.printStackTrace();
//                    }
//                });
//            } catch (SchedulerException e) {
//                // TODO add logger
//                e.printStackTrace();
//            }
//        });
//        return jobInfos;
//    }


    public QuartzServiceImpl(Scheduler scheduler) {
        super();
        this.scheduler = scheduler;
    }
}
