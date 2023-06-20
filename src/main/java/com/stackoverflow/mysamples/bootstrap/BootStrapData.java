package com.stackoverflow.mysamples.bootstrap;

import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.stackoverflow.mysamples.config.SimpleJobSubmitter;
import com.stackoverflow.mysamples.entity.Cat;
import com.stackoverflow.mysamples.entity.CustomPet;
import com.stackoverflow.mysamples.entity.Dog;
import com.stackoverflow.mysamples.entity.Owner;
import com.stackoverflow.mysamples.entity.PetPersistable;
import com.stackoverflow.mysamples.entity.Toy;
import com.stackoverflow.mysamples.repository.OwnerRepository;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

  private final OwnerRepository ownerRepository;
  private final SimpleJobSubmitter jobSubmitter;

  @Override
  public void run(String... args) throws Exception {
    PetPersistable cat = new Cat();
    cat.setName("Oscar");
    cat.setAge(6);
    cat.setFavoriteToy(new Toy().setName("Bone").setMadeOf("bone"));

    PetPersistable dog = new Dog();
    dog.setName("Lucky");
    dog.setAge(10);
    dog.setFavoriteToy(new Toy().setName("Cat").setMadeOf("wool"));

    PetPersistable dog1 = new Dog();
    dog1.setName("Moe");
    dog1.setAge(12);
    dog1.setFavoriteToy(new Toy().setName("Log").setMadeOf("wood"));

    PetPersistable dog2 = new Dog();
    dog2.setName("Dana");
    dog2.setAge(2);
    dog2.setFavoriteToy(new Toy().setName("Baby").setMadeOf("flash"));

    var customPet = new CustomPet();
    customPet.setName("Smoking kills");
    customPet.setFavoriteToy(new Toy().setName("Voodoo").setMadeOf("cotton"));

    Set<PetPersistable> pets = Set.of(cat, dog, customPet, dog1, dog2);

    //    petRepository.saveAll(pets);

    var owner = new Owner();
    owner.setLastName("Doe");
    owner.setFirstName("John");
    owner.setPets(pets);

    ownerRepository.save(owner);

    var owner2 = new Owner();
    owner2.setLastName("Smoke");
    owner2.setFirstName("Yuriy");
    owner2 = ownerRepository.save(owner2);

    System.out.println("throwException(AuxTestUtils.java:17)");
    System.out.println("throwException(AuxTestUtils.java:17)".replaceAll("java:\\d+", ""));
    System.out.println("Database was successfully initialized with data: ");
    System.out.println(cat);
    System.out.println(dog);
    System.out.println(dog1);
    System.out.println(dog2);
    System.out.println(customPet);
    System.out.println(owner);
    System.out.println(owner2);

    PetPersistable forSubmit = new Cat();
    forSubmit.setAge(1);
    forSubmit.setName("Button");
    jobSubmitter.submitPetJob(new Dog(), UUID.randomUUID());
    jobSubmitter.submitPetJob(new Cat(), UUID.randomUUID());
    jobSubmitter.submitOwnerJob(owner2, UUID.randomUUID());
  }
}
