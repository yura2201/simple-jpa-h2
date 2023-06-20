package com.stackoverflow.mysamples.config;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackoverflow.mysamples.config.SchedulerConfig.Params;
import com.stackoverflow.mysamples.entity.Owner;
import com.stackoverflow.mysamples.entity.Pet;
import com.stackoverflow.mysamples.service.quartz.OwnerReassigner;
import com.stackoverflow.mysamples.service.quartz.PetPopulator;

/**
 * @author ytsarkov (yurait6@gmail.com) on 20.06.2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SimpleJobSubmitter {

  private final ObjectMapper objectMapper;
  private final SchedulerConfig schedulerConfig;
  private final Scheduler scheduler;

  public void submitPetJob(Pet pet, UUID salt) throws JsonProcessingException {
    JobDetail jd = createJobDetail(PetPopulator.class, getPetJobData(pet), "Create_a_pet." + salt.toString());
    SimpleTrigger tr = createTrigger(jd, QuartzConfig.TRIGGER_NAME_CREATE_PET_ON_DEMAND + "." + salt,
                                     schedulerConfig.getPetParams());
    try {
      scheduler.scheduleJob(jd, tr);
    } catch (SchedulerException ex) {
      log.error("submitPetJob: Unable to submit a Job: jobName=[{}], triggerName=[{}]",
                jd.getKey(), tr.getKey(), ex);
    }
  }

  private JobDataMap getPetJobData(Pet pet) throws JsonProcessingException {
    JobDataMap dataMap = new JobDataMap();
    dataMap.put("pet", objectMapper.writeValueAsString(pet));
    dataMap.put("petType", pet.getClass().getName());
    return dataMap;
  }

  public void submitOwnerJob(Owner owner, UUID salt) throws JsonProcessingException {
    JobDetail jd = createJobDetail(OwnerReassigner.class, getOwnerDataMap(owner), "Assign_an_owner." + salt.toString());
    SimpleTrigger tr = createTrigger(jd, QuartzConfig.TRIGGER_NAME_ASSIGN_OWNER_ON_DEMAND + "." + salt,
                                     schedulerConfig.getOwnerParams());
    try {
      scheduler.scheduleJob(jd, tr);
    } catch (SchedulerException ex) {
      log.error("submitPetJob: Unable to submit a Job: jobName=[{}], triggerName=[{}]",
                jd.getKey(), tr.getKey(), ex);
    }
  }

  private JobDataMap getOwnerDataMap(Owner owner) throws JsonProcessingException {
    JobDataMap dataMap = new JobDataMap();
    dataMap.put("owner", objectMapper.writeValueAsString(owner));
    return dataMap;
  }

  private SimpleTrigger createTrigger(JobDetail jobDetail, String triggerName, Params params) {
    log.debug("createTrigger(jobDetail={}, triggerName={})", jobDetail.toString(), triggerName);
    return TriggerBuilder.newTrigger().forJob(jobDetail)
        .withIdentity(triggerName, QuartzConfig.JOB_GROUP_SUBSCRIBE)
        .forJob(jobDetail.getKey())
        .withSchedule(SimpleScheduleBuilder
                          .simpleSchedule()
                          .withRepeatCount(params.getRepeatCount())
                          .withIntervalInSeconds(params.getRepeatInterval())
                          .withMisfireHandlingInstructionNextWithRemainingCount())
        .build();
  }

  private JobDetail createJobDetail(Class<? extends Job> jobClass, JobDataMap jobData, String jobName)
      throws JsonProcessingException {
    log.debug("createJobDetail(jobClass={}, jobName={})", jobClass.getName(), jobName);
    return JobBuilder.newJob(jobClass)
        .withIdentity(jobName, QuartzConfig.JOB_GROUP_SUBSCRIBE)
        .storeDurably()
        .requestRecovery(true)
        .setJobData(jobData)

        .build();
  }
}
