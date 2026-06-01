package ru.job4j.devops.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.models.enums.CalcType;
import ru.job4j.devops.repository.CalcEventRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalcEventService {
  private final CalcEventRepository eventRepository;

  /**
   * Сложение переменных.
   *
   * @param user {@link User}
   * @param first int
   * @param second int
   * @return {@link CalcEvent}
   */
  public CalcEvent addEvent(User user, int first, int second) {
    var event = new CalcEvent();
    event.setUser(user);
    event.setFirst(BigDecimal.valueOf(first));
    event.setSeconds(BigDecimal.valueOf(second));
    event.setResult(BigDecimal.valueOf(first + second));
    event.setType(CalcType.SUMMATION);
    event.setCreateDate(LocalDateTime.now());
    log.debug("Save event: {}", event);
    return eventRepository.save(event);
  }
}
