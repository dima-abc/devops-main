package ru.job4j.devops.container;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public interface PostgreSQLContainerInitializer {
  PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
      new PostgreSQLContainer<>("postgres:16-alpine")
          .withReuse(true)
          .withInitScript("db/init_schema.sql");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
  }

  @BeforeAll
  static void beforeAll() {
    POSTGRE_SQL_CONTAINER.start();
  }
}
