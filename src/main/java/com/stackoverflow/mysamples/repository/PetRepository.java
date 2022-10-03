package com.stackoverflow.mysamples.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.stackoverflow.mysamples.entity.PetPersistable;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
public interface PetRepository extends CrudRepository<PetPersistable, Long> {

  List<PetPersistable> findAll();
}
