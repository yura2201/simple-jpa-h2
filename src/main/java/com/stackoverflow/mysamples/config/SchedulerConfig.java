package com.stackoverflow.mysamples.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author ytsarkov (yurait6@gmail.com) on 20.06.2023
 */
@ConfigurationProperties(prefix = "scheduler.config")
@ConstructorBinding
@Getter
@Setter
public class SchedulerConfig {

  private Params petParams;
  private Params ownerParams;

  @Getter
  @Setter
  public static class Params {
    private int repeatCount;
    private int repeatInterval;
  }
}
