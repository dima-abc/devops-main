package ru.job4j.devops.container;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIT implements PostgreSQLContainerInitializer, KafkaContainerInitializer {}
