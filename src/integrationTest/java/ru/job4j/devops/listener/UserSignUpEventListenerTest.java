package ru.job4j.devops.listener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import ru.job4j.devops.container.KafkaContainerInitializer;
import ru.job4j.devops.container.PostgreSQLContainerInitializer;
import ru.job4j.devops.models.User;
import ru.job4j.devops.repository.UserRepository;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserSignUpEventListenerTest
    implements PostgreSQLContainerInitializer, KafkaContainerInitializer {
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
