package com.stackoverflow.mysamples.service;

import java.util.List;

import com.stackoverflow.mysamples.entity.PetPersistable;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 20.09.2022
 */
public interface ZooService {
  void interact();

  List<PetPersistable> getPets();
}
