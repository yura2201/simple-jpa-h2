package com.stackoverflow.mysamples.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.stackoverflow.mysamples.entity.Toy;
import com.stackoverflow.mysamples.repository.OwnerRepository;
import com.stackoverflow.mysamples.repository.PetRepository;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 19.09.2022
 */
@Service
public class ZooServiceImpl implements ZooService {

  private final PetRepository petRepository;
  private final OwnerRepository ownerRepository;

  public ZooServiceImpl(PetRepository petRepository, OwnerRepository ownerRepository) {
    this.petRepository = petRepository;
    this.ownerRepository = ownerRepository;
  }

  @Override
  public List<Toy> getToysNameAsc(Collection<Long> ids) {
    return petRepository.findDistinctByIdIn(ids, Sort.by(new Order(Direction.ASC, "favoriteToy.name")));
  }

  @Override
  public List<Toy> getToysNameDesc(Collection<Long> ids) {
    return petRepository.findDistinctByIdIn(ids, Sort.by(new Order(Direction.DESC, "favoriteToy.name")));
  }

  @Override
  public Set<Toy> getToysSetNameAsc(Collection<Long> ids) {
    return petRepository.findDistinctSetByIdIn(ids, Sort.by(new Order(Direction.ASC, "favoriteToy.name")));
  }

  @Override
  public Set<Toy> getToysSetNameDesc(Collection<Long> ids) {
    return petRepository.findDistinctSetByIdIn(ids, Sort.by(new Order(Direction.DESC, "favoriteToy.name")));
  }
}
