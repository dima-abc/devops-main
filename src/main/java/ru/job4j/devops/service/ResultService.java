package ru.job4j.devops.service;

import java.util.List;
import ru.job4j.devops.models.Result;

public interface ResultService {
  void save(Result result);

  List<Result> findAll();
}
