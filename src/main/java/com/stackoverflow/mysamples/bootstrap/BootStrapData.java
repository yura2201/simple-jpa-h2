package com.stackoverflow.mysamples.bootstrap;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.stackoverflow.mysamples.entity.Cat;
import com.stackoverflow.mysamples.entity.CustomPet;
import com.stackoverflow.mysamples.entity.Dog;
import com.stackoverflow.mysamples.entity.PetPersistable;
import com.stackoverflow.mysamples.repository.PetRepository;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Component
public class BootStrapData implements CommandLineRunner {

  private final PetRepository petRepository;

  public BootStrapData(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    PetPersistable cat = new Cat();
    cat.setName("Oscar");
    cat.setAge(6);

    PetPersistable dog = new Dog();
    dog.setName("Lucky");
    dog.setAge(10);

    var customPet = new CustomPet();
    customPet.setName("Smoking kills");

    petRepository.saveAll(List.of(cat, dog, customPet));

    System.out.println("Database was successfully initialized with data: ");
    System.out.println(cat);
    System.out.println(dog);
  }
}
