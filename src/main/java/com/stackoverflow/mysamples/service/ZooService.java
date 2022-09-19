package com.stackoverflow.mysamples.service;

import org.springframework.stereotype.Service;

import com.stackoverflow.mysamples.entity.Cat;
import com.stackoverflow.mysamples.repository.PetRepository;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Service
public class ZooService {

  private final PetRepository petRepository;

  public ZooService(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  public void interact() {
    Cat pet1 = (Cat) petRepository.findById(1L).get();
    var pet2 = petRepository.findById(2L).get();
    var pet = petRepository.findById(3L).get();
    //    petRepository.saveAll(List.of(pet1, pet2, newPet));
    System.out.println(pet1);
    System.out.println(pet2);
    System.out.println(pet);
  }
}
