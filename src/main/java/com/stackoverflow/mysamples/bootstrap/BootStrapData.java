package com.stackoverflow.mysamples.bootstrap;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.stackoverflow.mysamples.entity.Cat;
import com.stackoverflow.mysamples.entity.CustomPet;
import com.stackoverflow.mysamples.entity.Dog;
import com.stackoverflow.mysamples.entity.Owner;
import com.stackoverflow.mysamples.entity.PetPersistable;
import com.stackoverflow.mysamples.entity.Toy;
import com.stackoverflow.mysamples.repository.OwnerRepository;
import com.stackoverflow.mysamples.repository.PetRepository;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Component
public class BootStrapData implements CommandLineRunner {

  private final PetRepository petRepository;
  private final OwnerRepository ownerRepository;

  public BootStrapData(PetRepository petRepository, OwnerRepository ownerRepository) {
    this.petRepository = petRepository;
    this.ownerRepository = ownerRepository;
  }

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

    var customPet = new CustomPet();
    customPet.setName("Smoking kills");
    customPet.setFavoriteToy(new Toy().setName("Voodoo").setMadeOf("cotton"));

    Set<PetPersistable> pets = Set.of(cat, dog, customPet);

    //    petRepository.saveAll(pets);

    var owner = new Owner();
    //    owner.setLastName("Doe");
    owner.setFirstName("John");
    owner.setPets(pets);

    ownerRepository.save(owner);

    System.out.println("Database was successfully initialized with data: ");
    System.out.println(cat);
    System.out.println(dog);
    System.out.println(customPet);
    System.out.println(owner);
  }
}
