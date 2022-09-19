package com.stackoverflow.mysamples.entity;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.stackoverflow.mysamples.dictionary.PetTypes;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@MappedSuperclass
public abstract class Pet {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue
  private Long id;

  @Column
  private String name;

  @Column
  private Integer age;

  @Column
  private Integer typeCode;

  @Formula("case type_code when 1 then 'Cat' when 2 then 'Dog' else 'Chupacabra' end")
  private String typeName;

  @Transient
  private PetTypes type;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Integer getTypeCode() {
    return typeCode;
  }

  protected void setTypeCode(Integer typeCode) {
    this.typeCode = typeCode;
  }

  public String getTypeName() {
    return typeName;
  }

  public PetTypes getType() {
    return initAndGetPetType();
  }

  private PetTypes initAndGetPetType() {
    return Optional.ofNullable(typeCode).map(code -> {
      if (Objects.nonNull(type)) {
        return type;
      }
      type = PetTypes.of(code);
      return type;
    }).orElse(null);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Pet.class.getSimpleName() + "[", "]")
        .add("id=" + getId())
        .add("name='" + getName() + "'")
        .add("age=" + getAge())
        .add("typeCode=" + getTypeCode())
        .add("typeName='" + getTypeName() + "'")
        .add("type=" + getType())
        .toString();
  }
}
