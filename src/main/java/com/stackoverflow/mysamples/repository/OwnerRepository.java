package com.stackoverflow.mysamples.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.stackoverflow.mysamples.entity.Owner;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 20.09.2022
 */
public interface OwnerRepository extends CrudRepository<Owner, Long> {

  @Query("select o from Owner o join fetch o.pets where o.id = :id")
  Optional<Owner> findEagerById(Long id);
}
