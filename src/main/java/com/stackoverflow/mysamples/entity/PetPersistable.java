package com.stackoverflow.mysamples.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Entity
@Table(name = "pet")
public abstract class PetPersistable extends Pet {

  public PetPersistable() {
    initPetType();
  }

  public abstract void pingPet();

  protected abstract void initPetType();
}
