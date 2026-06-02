FROM gradle:8.11.1-jdk21 AS builder
RUN mkdir devops_main
WORKDIR /devops_main
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
RUN gradle --no-daemon dependencies
COPY . .
RUN gradle --no-daemon build
RUN jar xf /devops_main/build/libs/DevOps-1.0.0.jar
RUN jdeps --ignore-missing-deps -q  \
    --recursive  \
    --multi-release 21  \
    --print-module-deps  \
    --class-path 'BOOT-INF/lib/*'  \
    /devops_main/build/libs/DevOps-1.0.0.jar > deps.info
RUN jlink \
    --add-modules $(cat deps.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /slim-jre

FROM debian:bookworm-slim
ENV JAVA_HOME /user/java/jdk21
ENV PATH $JAVA_HOME/bin:$PATH
COPY --from=builder /slim-jre $JAVA_HOME
COPY --from=builder /devops_main/build/libs/DevOps-1.0.0.jar .

EXPOSE 8080
ENTRYPOINT java -jar DevOps-1.0.0.jar