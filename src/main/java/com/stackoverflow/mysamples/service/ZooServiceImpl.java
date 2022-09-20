package com.stackoverflow.mysamples.service;

import org.springframework.stereotype.Service;

import com.stackoverflow.mysamples.repository.OwnerRepository;
import com.stackoverflow.mysamples.repository.PetRepository;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Service
public class ZooServiceImpl implements ZooService {

  private final PetRepository petRepository;
  private final OwnerRepository ownerRepository;

  public ZooServiceImpl(PetRepository petRepository, OwnerRepository ownerRepository) {
    this.petRepository = petRepository;
    this.ownerRepository = ownerRepository;
  }

  @Override
  public void interact() {
    var pet1 = petRepository.findById(1L).get();
    var pet2 = petRepository.findById(2L).get();
    var pet = petRepository.findById(3L).get();
    //    petRepository.saveAll(List.of(pet1, pet2, newPet));
    var owner = ownerRepository.findById(1L).get();

    System.out.println(pet1);
    System.out.println(pet2);
    System.out.println(pet);
    System.out.println(owner);
  }
}
