# https://spring.io/guides/topicals/spring-boot-docker/
FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/*.jar bbaemin.jar
ENTRYPOINT ["java","-jar","/bbaemin.jar"]
