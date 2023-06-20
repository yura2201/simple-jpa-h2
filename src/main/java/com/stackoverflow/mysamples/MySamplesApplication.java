package com.stackoverflow.mysamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import com.stackoverflow.mysamples.config.SchedulerConfig;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = { SchedulerConfig.class })
public class MySamplesApplication {

  public static void main(String[] args) {
    SpringApplication.run(MySamplesApplication.class, args);
  }

}
