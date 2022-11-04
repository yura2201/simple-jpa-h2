package com.stackoverflow.mysamples.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.stackoverflow.mysamples.entity.PetPersistable;
import com.stackoverflow.mysamples.entity.Toy;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 20.09.2022
 */
public interface ZooService {

  void interact();

  List<PetPersistable> getPets();
  
  List<Toy> getToysNameAsc(Collection<Long> ids);

  List<Toy> getToysNameDesc(Collection<Long> ids);

  Set<Toy> getToysSetNameAsc(Collection<Long> ids);

  Set<Toy> getToysSetNameDesc(Collection<Long> ids);
}
