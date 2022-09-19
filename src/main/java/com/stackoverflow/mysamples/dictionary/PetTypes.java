package com.stackoverflow.mysamples.dictionary;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
public enum PetTypes {

  CAT(1, "Cat"),
  DOG(2, "Dog");

  private final int code;

  private final String value;

  PetTypes(int code, String value) {
    this.code = code;
    this.value = value;
  }

  public static PetTypes of(Integer code) {
    return Stream.of(PetTypes.values()).filter(val -> Objects.equals(val.code, code)).findFirst().orElse(null);
  }

  public int typeCode() {
    return code;
  }
}
