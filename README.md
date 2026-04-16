### DevOps - учебный проект.

Это простое Spring boot приложение созданное для отработки DevOps.
1. Создание pipeline с Jenkins.
2. Разбор задач CI/CD
3. Интеграция с Kubernetes.
4. Работа с мониторингом Prometheus, OpenTelemetry, Grafana.

В качестве системы сборки используется gradle.

```
gradle build 
```

Для валидация кода подключены плагины:

```
checkstyle
jacoco
```

