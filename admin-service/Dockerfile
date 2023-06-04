FROM openjdk:11

# build
ARG PACKAGE_JAR=build/libs/admin-service-1.0-SNAPSHOT.jar

# run
EXPOSE 9000:9000
ADD ${PACKAGE_JAR} admin-service.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=dev", "./admin-service.jar"]
