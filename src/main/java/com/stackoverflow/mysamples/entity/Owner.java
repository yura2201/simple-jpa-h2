package com.stackoverflow.mysamples.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 20.09.2022
 */
@Entity
public class Owner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @OneToMany(mappedBy = "owner",
      fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<PetPersistable> pets;

  @Column
  private String lastName;

  @Column
  private String firstName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<PetPersistable> getPets() {
    return pets;
  }

  public void setPets(Set<PetPersistable> pets) {
    this.pets = pets;
    if (!CollectionUtils.isEmpty(pets)) {
      this.pets.forEach(pet -> pet.setOwner(this));
    }
  }

  public void addPet(PetPersistable pet) {
    pet.setOwner(this);
    Optional.ofNullable(pets).orElse(new HashSet<>(1)).add(pet);
  }

  public boolean removePet(PetPersistable pet) {
    if (Objects.nonNull(pet)) {
      pet.setOwner(null);
      return pets.remove(pet);
    }
    return false;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Owner owner = (Owner) o;

    if (getId() != null ? !getId().equals(owner.getId()) : owner.getId() != null) {
      return false;
    }
    if (getLastName() != null ? !getLastName().equals(owner.getLastName()) : owner.getLastName() != null) {
      return false;
    }
    return getFirstName() != null ? getFirstName().equals(owner.getFirstName()) : owner.getFirstName() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
    result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Owner.class.getSimpleName() + "[", "]")
        .add("id=" + getId())
        .add("lastName='" + getLastName() + "'")
        .add("firstName='" + getFirstName() + "'")
        .toString();
  }
}
