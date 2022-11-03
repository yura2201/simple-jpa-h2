package com.stackoverflow.mysamples.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.stackoverflow.mysamples.entity.PetPersistable;
import com.stackoverflow.mysamples.entity.Toy;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@RepositoryRestResource(collectionResourceRel = "pets", path = "pets")
public interface PetRepository extends Repository<PetPersistable, Long> {

  @Query("select distinct p.favoriteToy from PetPersistable p where p.id in :ids")
  List<Toy> findDistinctByIdIn(Collection<Long> ids, Sort sort);

  @Query("select distinct p.favoriteToy from PetPersistable p where p.id in :ids")
  Set<Toy> findDistinctSetByIdIn(Collection<Long> ids, Sort sort);
}
