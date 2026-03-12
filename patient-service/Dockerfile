FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

#this is for downloading all the dependencies before copying the source code to take advantage of docker caching
#and caching when the image down so no need to download it each time we build the image
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

COPY --from=builder /app/target/patient-management.jar ./app.jar


EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]

#CMD ["--spring.profiles.active=prod"]
