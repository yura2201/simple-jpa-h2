package com.stackoverflow.mysamples.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import com.stackoverflow.mysamples.entity.Toy;

/**
 * Created by Yuriy Tsarkov on 19.09.2022
 */
@SpringBootTest
class ZooServiceIntegrationTest {

  @Autowired
  TransactionTemplate transactionTemplate;

  @Autowired
  private ZooService zooService;

  @Test
  void testOrder() {
    List<Toy> toysAsc = zooService.getToysNameAsc(List.of(1L, 3L));
    List<Toy> toysDesc = zooService.getToysNameDesc(List.of(1L, 3L));
    System.out.println(toysAsc);
    System.out.println(toysDesc);
  }

  @Test
  void testOrder_set() {
    List<Long> ids = List.of(1L, 3L);
    List<Toy> toyListAsc = zooService.getToysNameAsc(ids);
    Set<Toy> toysAsc = (Set<Toy>) doInTx(() -> zooService.getToysSetNameAsc(ids));
    Set<Toy> toysDesc = (Set<Toy>) doInTx(() -> zooService.getToysSetNameDesc(ids));
    assertThat(toysAsc, containsInRelativeOrder(toyListAsc.get(0), toyListAsc.get(1)));
    assertThat(toysDesc, containsInRelativeOrder(toyListAsc.get(1), toyListAsc.get(0)));
  }

  Collection<Toy> doInTx(Supplier<Collection<Toy>> toySupplier) {
    return transactionTemplate.execute(
        (tx) -> {
          Collection<Toy> collection = toySupplier.get();
          System.out.println(collection);
          return collection;
        }
    );
  }
}