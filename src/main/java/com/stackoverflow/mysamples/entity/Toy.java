package com.stackoverflow.mysamples.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 03.11.2022
 */
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Toy {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String madeOf;

  public Long getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Toy toy = (Toy) o;

    if (getId() != null ? !getId().equals(toy.getId()) : toy.getId() != null)
      return false;
    if (getName() != null ? !getName().equals(toy.getName()) : toy.getName() != null)
      return false;
    return getMadeOf() != null ? getMadeOf().equals(toy.getMadeOf()) : toy.getMadeOf() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getMadeOf() != null ? getMadeOf().hashCode() : 0);
    return result;
  }
}
