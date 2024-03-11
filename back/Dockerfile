FROM maven:latest as stage1
WORKDIR /back
COPY pom.xml /back
RUN mvn dependency:resolve
COPY . /back
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:21 as final
COPY --from=stage1 /back/target/*.jar back.jar
EXPOSE 8080
CMD ["java", "-jar", "back.jar"]