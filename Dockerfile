FROM openjdk:21-jdk AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
