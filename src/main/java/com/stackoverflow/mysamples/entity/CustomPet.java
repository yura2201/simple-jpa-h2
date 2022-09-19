package com.stackoverflow.mysamples.entity;

import javax.persistence.Entity;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Entity
public class CustomPet extends PetPersistable {

  @Override
  public void pingPet() {
    System.out.println("agr-r-r-r-h-h!");
  }

  @Override
  protected void initPetType() {
    // foo
  }
}
