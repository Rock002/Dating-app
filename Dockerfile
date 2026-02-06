FROM eclipse-temurin:24-jdk

WORKDIR /app

COPY target/MeowDate-0.0.1-SNAPSHOT.jar ./app/MeowDate-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "MeowDate-0.0.1-SNAPSHOT.jar"]