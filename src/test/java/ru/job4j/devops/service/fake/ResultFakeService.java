package ru.job4j.devops.service.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.service.ResultService;

public class ResultFakeService implements ResultService {
  private final Map<Long, Result> memResults = new HashMap<Long, Result>();
  private long genId = 1;

  @Override
  public void save(Result result) {
    if (Objects.isNull(result.getId())) {
      result.setId(genId++);
    }
    memResults.put(result.getId(), result);
  }

  @Override
  public List<Result> findAll() {
    return List.copyOf(memResults.values());
  }
}
