package com.stackoverflow.mysamples.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

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
  }

  public void addPet(PetPersistable pet) {
    Optional.ofNullable(pets).orElse(new HashSet<>(1)).add(pet);
  }

  public boolean removePet(PetPersistable pet) {
    if (Objects.nonNull(pet)) {
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
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Owner owner = (Owner) o;

    if (getId() != null ? !getId().equals(owner.getId()) : owner.getId() != null)
      return false;
    if (getLastName() != null ? !getLastName().equals(owner.getLastName()) : owner.getLastName() != null)
      return false;
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
