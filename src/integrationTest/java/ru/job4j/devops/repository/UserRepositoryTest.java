package ru.job4j.devops.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.devops.container.PostgreSQLContainerInitializer;
import ru.job4j.devops.models.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserRepositoryTest implements PostgreSQLContainerInitializer {
  @Autowired private UserRepository userRepository;

  @Test
  public void whenSaveUser() {
    // GIVEN
    var user = new User();
    user.setUserName("devops");
    // WHEN
    var actual = userRepository.save(user);
    // THEN
    assertThat(userRepository.findById(actual.getId())).contains(user);
  }
}
