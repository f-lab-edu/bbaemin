# build
FROM openjdk:11 as builder
WORKDIR /app

# gradle 파일이 변경되었을 경우에 새롭게 의존 패키지 다운로드
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle
RUN ./gradlew build -x test --parallel --continue > /dev/null 2>&1 || true

# builder image에서 application build
COPY . /app/src
RUN ./gradlew clean build -x test --parallel

# stage
FROM openjdk:11
COPY --from=builder app/bbaemin-api/build/libs/*.jar .
ENTRYPOINT ["java","-jar","/bbaemin.jar"]
