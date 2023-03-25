# build
FROM openjdk:11 as builder
WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle
RUN chmod +x gradlew
RUN ./gradlew build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /app/src
RUN ./gradlew clean build

# stage
FROM openjdk:11
COPY --from=builder app/bbaemin-api/build/libs/*.jar .
ENTRYPOINT ["java","-jar","/bbaemin.jar"]
