package com.stackoverflow.mysamples.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import org.hibernate.annotations.Formula;

import com.stackoverflow.mysamples.dictionary.PetTypes;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@MappedSuperclass
public abstract class Pet {

  @Transient
  public String ownerLastName;
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "r_owner_id", referencedColumnName = "id")
  private Owner owner;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "r_toy_id", referencedColumnName = "id")
  private Toy favoriteToy;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @PostLoad
  void initTransient() {
    ownerLastName = Optional.ofNullable(getOwner()).map(Owner::getLastName).orElse("'Doe'");
  }

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

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public Toy getFavoriteToy() {
    return favoriteToy;
  }

  public void setFavoriteToy(Toy favoriteToy) {
    this.favoriteToy = favoriteToy;
  }

  public String getOwnerLastName() {
    return ownerLastName;
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Pet pet = (Pet) o;

    if (getId() != null ? !getId().equals(pet.getId()) : pet.getId() != null) {
      return false;
    }
    if (getName() != null ? !getName().equals(pet.getName()) : pet.getName() != null) {
      return false;
    }
    if (getAge() != null ? !getAge().equals(pet.getAge()) : pet.getAge() != null) {
      return false;
    }
    return getTypeCode() != null ? getTypeCode().equals(pet.getTypeCode()) : pet.getTypeCode() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getAge() != null ? getAge().hashCode() : 0);
    result = 31 * result + (getTypeCode() != null ? getTypeCode().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Pet.class.getSimpleName() + "[", "]")
        .add("id=" + getId())
        .add("name='" + getName() + "'")
        .add("age=" + getAge())
        .add("ownerLastName=" + getOwnerLastName())
        .add("typeCode=" + getTypeCode())
        .add("typeName='" + getTypeName() + "'")
        .add("type=" + getType())
        .toString();
  }
}
