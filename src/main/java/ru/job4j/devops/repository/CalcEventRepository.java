package ru.job4j.devops.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.devops.models.CalcEvent;

import java.util.Optional;

public interface CalcEventRepository extends CrudRepository<CalcEvent, Long> {
  Optional<CalcEvent> findFirstByUserId(Long userId);
}
