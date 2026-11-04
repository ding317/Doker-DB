FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# 1. Salin pom.xml dan download dependency terlebih dahulu (direkatkan di cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Salin source code setelah dependency siap
COPY src ./src
RUN mvn package -DskipTests

# 3. Stage runner (ringan)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "app.jar"]