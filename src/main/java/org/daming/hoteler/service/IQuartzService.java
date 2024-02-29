package org.daming.hoteler.service;

import org.daming.hoteler.pojo.JobInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.List;

/**
 * @author daming
 * @version 2023-07-02 18:21
 **/
public interface IQuartzService {

    void addJob(String name, String group, String cron, String className) throws SchedulerException;

    void addJob(String name, String group, String cron, Class<? extends Job> clazz) throws SchedulerException;

    void addJob(String name, String group, String cron, Class<? extends Job> clazz, JobDataMap jobDataMap) throws SchedulerException;

    JobInfo getJob(String name, String group) throws SchedulerException;

    boolean modifyJob(String name, String group, String cron) throws SchedulerException;

    boolean deleteJob(String name, String group) throws SchedulerException;

    boolean pauseJob(String name, String group) throws SchedulerException;

    boolean resumeJob(String name, String group) throws SchedulerException;

    List<JobInfo> listJob() throws SchedulerException;
}
