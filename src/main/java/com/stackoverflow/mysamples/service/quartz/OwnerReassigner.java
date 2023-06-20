package com.stackoverflow.mysamples.service.quartz;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackoverflow.mysamples.entity.Owner;
import com.stackoverflow.mysamples.entity.PetPersistable;
import com.stackoverflow.mysamples.exception.OperationException;
import com.stackoverflow.mysamples.repository.OwnerRepository;
import com.stackoverflow.mysamples.repository.PetRepository;

/**
 * @author ytsarkov (yurait6@gmail.com) on 19.06.2023
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@RequiredArgsConstructor
@Slf4j
public class OwnerReassigner implements Job {

  private final PetRepository petRepository;
  private final OwnerRepository ownerRepository;
  private final ObjectMapper objectMapper;

  @SneakyThrows
  @Override
  public void execute(JobExecutionContext context) {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    log.info("Job: {}", context.getJobDetail().getKey().getName());
    Optional<PetPersistable> petOpt = petRepository.findOneByOwnerIsNull();
    if (petOpt.isPresent()) {
      Owner owner = getOrCreateOwner(jobDataMap.getString("owner"));
      PetPersistable pet = petOpt.get();
      pet.setOwner(owner);
      petRepository.save(pet);
    } else {
      log.info("All the pets are settled on. Finish the Job");
      Scheduler scheduler = context.getScheduler();
      scheduler.pauseJob(context.getJobDetail().getKey());
      scheduler.deleteJob(context.getJobDetail().getKey());
    }
  }

  private Owner getOrCreateOwner(String ownerJson) throws JsonProcessingException {
    Owner owner = objectMapper.readValue(ownerJson, Owner.class);
    if (Objects.isNull(owner)) {
      log.error("getOrCreateOwner: invalid owner assign!");
      throw new OperationException("Invalid owner for assign!");
    }
    Optional<Owner> dbOwner = ownerRepository.findEagerById(owner.getId());
    return dbOwner.orElseGet(() -> {
      log.info("getOrCreateOwner: owner not found and will be created");
      return ownerRepository.save(owner);
    });
  }
}


