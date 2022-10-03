package com.stackoverflow.mysamples.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by Yuriy Tsarkov on 19.09.2022
 */
@SpringBootTest
class ZooServiceIntegrationTest {

  @Autowired
  private ZooService zooService;

  @Test
  void interact() {
    zooService.interact();
  }
}