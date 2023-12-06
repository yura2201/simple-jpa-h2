package com.stackoverflow.mysamples.entity;

import jakarta.persistence.Entity;

import com.stackoverflow.mysamples.dictionary.PetTypes;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Entity
public class Cat extends PetPersistable {

  @Override
  protected void initPetType() {
    this.setTypeCode(PetTypes.CAT.typeCode());
  }

  @Override
  public void pingPet() {
    System.out.println("Me-e-o-w");
  }
}
