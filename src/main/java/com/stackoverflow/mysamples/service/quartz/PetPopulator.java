package com.stackoverflow.mysamples.service.quartz;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackoverflow.mysamples.entity.PetPersistable;
import com.stackoverflow.mysamples.repository.PetRepository;

/**
 * @author ytsarkov (yurait6@gmail.com) on 20.06.2023
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@RequiredArgsConstructor
@Slf4j
public class PetPopulator implements Job {

  private final PetRepository petRepository;
  private final ObjectMapper objectMapper;

  @SneakyThrows
  @Override
  public void execute(JobExecutionContext context) {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    log.info("Job: {}", context.getJobDetail().getKey().getName());
    String formattedMap = jobDataMap.entrySet().
        stream()
        .map(e -> e.getKey() + " = " + e.getValue())
        .collect(Collectors.joining(";"));
    log.info("Job dataMap: {}", formattedMap);
    PetPersistable pet = (PetPersistable) objectMapper.readValue(jobDataMap.getString("pet"),
                                                                 Class.forName(jobDataMap.getString("petType")));
    petRepository.save(pet);
  }
}
