package ru.job4j.devops.listener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import ru.job4j.devops.container.KafkaContainerInitializer;
import ru.job4j.devops.container.PostgreSQLContainerInitializer;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.models.enums.CalcType;
import ru.job4j.devops.repository.CalcEventRepository;
import ru.job4j.devops.repository.UserRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CalcEventListenerTest
    implements PostgreSQLContainerInitializer, KafkaContainerInitializer {
  @Autowired private KafkaTemplate<String, Object> kafkaTemplate;
  @Autowired private UserRepository userRepository;
  @Autowired private CalcEventRepository calcEventRepository;

  @Test
  void whenSignUpEvent() {
    // GIVEN
    var user = new User();
    user.setUserName("devops123");
    userRepository.save(user);
    var calcEvent = new CalcEvent();
    calcEvent.setUser(user);
    calcEvent.setFirst(BigDecimal.ONE);
    calcEvent.setSeconds(BigDecimal.TWO);
    calcEvent.setResult(BigDecimal.ONE.add(BigDecimal.TWO));
    calcEvent.setType(CalcType.SUMMATION);
    // WHEN
    kafkaTemplate.send("signup-event", calcEvent);
    // THEN
    await()
        .pollInterval(Duration.ofSeconds(3))
        .atMost(10, TimeUnit.SECONDS)
        .untilAsserted(
            () -> assertThat(calcEventRepository.findFirstByUserId(user.getId())).isPresent());
  }
}
