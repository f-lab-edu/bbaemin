FROM openjdk:11 AS BUILD_IMAGE
WORKDIR /usr/app/
COPY settings.gradle gradlew /usr/app/
COPY gradle /usr/app/gradle
RUN ./gradlew -x test build || return 0
COPY ../../../Downloads .
RUN ./gradlew -x test build

FROM openjdk:11
WORKDIR /usr/app/
EXPOSE 8080
COPY --from=BUILD_IMAGE /usr/app/bbaemin-api/build/libs/bbaemin-api-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=dev", "bbaemin-api-1.0-SNAPSHOT.jar"]
