package com.stackoverflow.mysamples.config;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ytsarkov (yurait6@gmail.com) on 29.05.2023
 */
@Slf4j
@Configuration
public class QuartzConfig {

  public static final String JOB_GROUP_SUBSCRIBE = "JOB_GROUP_SUBSCRIBE";
  public static final String TRIGGER_NAME_CREATE_PET_ON_DEMAND = "CREATE_PET_ON_DEMAND";
  public static final String TRIGGER_NAME_ASSIGN_OWNER_ON_DEMAND = "ASSIGN_OWNER_ON_DEMAND";

  private final ApplicationContext applicationContext;
  private final DataSource dataSource;

  public QuartzConfig(ApplicationContext applicationContext, DataSource dataSource) {
    this.applicationContext = applicationContext;
    this.dataSource = dataSource;
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return mapper;
  }

  @Bean
  public SpringBeanJobFactory springBeanJobFactory() {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean
  public Scheduler scheduler(List<Trigger> triggers, List<JobDetail> jobDetails, SchedulerFactoryBean factoryBean)
      throws SchedulerException {
    Properties properties = new Properties();
    properties.setProperty("org.quartz.scheduler.instanceName", "MyInstanceName");
    properties.setProperty("org.quartz.scheduler.instanceId", "Instance1");
    factoryBean.setOverwriteExistingJobs(false);
    factoryBean.setAutoStartup(true);
    factoryBean.setQuartzProperties(properties);
    factoryBean.setDataSource(dataSource);
    factoryBean.setJobFactory(springBeanJobFactory());
    factoryBean.setWaitForJobsToCompleteOnShutdown(true);

    Scheduler scheduler = factoryBean.getScheduler();
    revalidateJobs(jobDetails, scheduler);
    rescheduleTriggers(triggers, scheduler);
    scheduler.start();

    return scheduler;
  }

  private void rescheduleTriggers(List<Trigger> triggers, Scheduler scheduler) {
    if (!CollectionUtils.isEmpty(triggers)) {
      triggers.forEach(tr -> {
        try {
          if (!scheduler.checkExists(tr.getKey())) {
            scheduler.scheduleJob(tr);
          } else {
            scheduler.rescheduleJob(tr.getKey(), tr);
          }
        } catch (SchedulerException ex) {
          log.error("rescheduleTriggers: Unable to schedule a Job: jobName=[{}], triggerName=[{}]",
                    tr.getJobKey(), tr.getKey(), ex);
        }
      });
    }
  }

  @SneakyThrows
  private void revalidateJobs(List<JobDetail> jobDetails, Scheduler scheduler) {
    if (!CollectionUtils.isEmpty(jobDetails)) {
      Map<JobKey, JobDetail> jobDetailMap = jobDetails.stream()
          .collect(Collectors.toMap(JobDetail::getKey, Function.identity()));
      scheduler.getJobKeys(GroupMatcher.jobGroupEquals(JOB_GROUP_SUBSCRIBE)).forEach(jk -> {
        if (!jobDetailMap.containsKey(jk)) {
          try {
            scheduler.deleteJob(jk);
          } catch (SchedulerException e) {
            log.error("revalidateJobs: Unable to delete Job: jobName=[{}]", jk);
          }
        }
      });
    }
  }
}
