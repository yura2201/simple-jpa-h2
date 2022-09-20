package com.stackoverflow.mysamples.repository;

import org.springframework.data.repository.CrudRepository;

import com.stackoverflow.mysamples.entity.Owner;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 20.09.2022
 */
public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
