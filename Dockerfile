# prepare JAR-file.
FROM eclipse-temurin:17-jdk-focal AS builder
WORKDIR application
# Copy and build
ARG JAR_FILE=target/sample-app.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# Declare container
FROM eclipse-temurin:17-jdk-focal
ENV PORT=8097
# Переменная, задающая режим запуска.
ENV DEBUG=false
# Переменная, задающая порт отладчика.
ENV DEBUG_PORT=8090
WORKDIR application
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
EXPOSE ${PORT:-8097}

# Run the app.
ENTRYPOINT if (${DEBUG} = "true"); then java -Dserver.port=${PORT} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:${DEBUG_PORT} org.springframework.boot.loader.launch.JarLauncher; else java -server -Dserver.port=${PORT} org.springframework.boot.loader.launch.JarLauncher; fi