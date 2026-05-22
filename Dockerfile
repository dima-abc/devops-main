FROM gradle:9.4.1-jdk21
RUN mkdir devops_main
WORKDIR /devops_main
COPY . .
RUN gradle clean build -x test
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/DevOps-1.0.0.jar"]
