package com.stackoverflow.mysamples.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stackoverflow.mysamples.entity.Cat;
import com.stackoverflow.mysamples.entity.Dog;
import com.stackoverflow.mysamples.entity.Pet;
import com.stackoverflow.mysamples.entity.PetPersistable;
import com.stackoverflow.mysamples.repository.PetRepository;

/**
 * Created by Yuriy Tsarkov on 03.10.2022
 */
@ExtendWith(MockitoExtension.class)
class ZooServiceUnitTest {

  @Mock
  PetRepository petRepository;

  @InjectMocks
  ZooServiceImpl zooService;

  @Test
  void getPets() {

    Cat cat = new Cat();
    cat.setId(1L);
    cat.setName("Cat_1");

    Dog dog = new Dog();
    dog.setId(2L);
    dog.setName("Dog_1");
    List<PetPersistable> pets = List.of(cat, dog);

    when(petRepository.findAll()).thenReturn(pets);
    List<PetPersistable> result = zooService.getPets();

    assertThat(result).extracting(Pet::getName).containsExactlyInAnyOrder("Cat_1", "Dog_1");
  }
}