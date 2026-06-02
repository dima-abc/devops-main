package ru.job4j.devops.listener;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import ru.job4j.devops.container.BaseIT;
import ru.job4j.devops.models.User;
import ru.job4j.devops.repository.UserRepository;

public class UserSignUpEventListenerTest extends BaseIT {
  @Autowired private KafkaTemplate<String, Object> kafkaTemplate;
  @Autowired private UserRepository userRepository;

  @Test
  void whenSignUpNewMember() {
    // GIVEN
    var user = new User();
    user.setUserName("devops");
    // WHEN
    kafkaTemplate.send("signup", user);
    // THEN
    await()
        .pollInterval(Duration.ofSeconds(3))
        .atMost(10, TimeUnit.SECONDS)
        .untilAsserted(
            () -> assertThat(userRepository.findByUserName(user.getUserName())).isPresent());
  }
}
