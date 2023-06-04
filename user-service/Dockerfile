FROM openjdk:11

# build
ARG PACKAGE_JAR=build/libs/user-service-1.0-SNAPSHOT.jar

# run
EXPOSE 9001:9001
ADD ${PACKAGE_JAR} user-service.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=dev", "./user-service.jar"]
