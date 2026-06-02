package ru.job4j.devops.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import ru.job4j.devops.container.PostgreSQLContainerInitializer;

public class TestContainersTest implements PostgreSQLContainerInitializer {

  @Test
  void whenSaveUser() {
    assertNotNull(POSTGRE_SQL_CONTAINER.getJdbcUrl());
    assertNotNull(POSTGRE_SQL_CONTAINER.getUsername());
    assertNotNull(POSTGRE_SQL_CONTAINER.getPassword());
  }
}
