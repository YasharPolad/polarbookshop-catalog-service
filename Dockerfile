FROM eclipse-temurin:17 AS builder
WORKDIR /app
COPY build/libs/catalog-service-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract


FROM eclipse-temurin:17-jre
RUN useradd spring
USER spring
WORKDIR /app
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
EXPOSE 9001
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]