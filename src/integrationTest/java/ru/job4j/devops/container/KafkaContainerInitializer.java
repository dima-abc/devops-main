package ru.job4j.devops.container;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public interface KafkaContainerInitializer {
  KafkaContainer KAFKA_CONTAINER =
      new KafkaContainer(DockerImageName.parse("apache/kafka:3.7.2")).withReuse(true);

  @BeforeAll
  static void beforeAll() {
    KAFKA_CONTAINER.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
    registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
  }
}
