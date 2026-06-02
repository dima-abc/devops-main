package ru.job4j.devops.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.devops.container.PostgreSQLContainerInitializer;
import ru.job4j.devops.models.User;
import ru.job4j.devops.models.enums.CalcType;
import ru.job4j.devops.repository.CalcEventRepository;
import ru.job4j.devops.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CalcEventServiceTest implements PostgreSQLContainerInitializer {
  @Autowired private UserRepository userRepository;
  @Autowired private CalcEventRepository calcEventRepository;
  @Autowired private CalcEventService calcEventService;

  @Test
  public void testCalcEventService() {
    // GIVEN
    var user = new User();
    user.setUserName("devops");
    userRepository.save(user);
    int firs = 100;
    int second = 200;
    // WHEN
    var calcEvent = calcEventService.addEvent(user, firs, second);
    // THEN
    assertThat(calcEventRepository.findById(calcEvent.getId()))
        .hasValueSatisfying(
            event -> {
              assertThat(event.getUser()).isEqualTo(user);
              assertThat(event.getFirst().intValue()).isEqualTo(firs);
              assertThat(event.getSeconds().intValue()).isEqualTo(second);
              assertThat(event.getResult().compareTo(BigDecimal.valueOf(firs + second)))
                  .isEqualTo(0);
              assertThat(event.getCreateDate().truncatedTo(ChronoUnit.MINUTES))
                  .isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
              assertThat(event.getType()).isEqualTo(CalcType.SUMMATION);
            });
  }
}
