package com.stackoverflow.mysamples.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;

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
