FROM openjdk:11
ARG JAR_FILE=bbaemin-api/build/libs/*.jar
COPY ${JAR_FILE} bbaemin.jar
ENTRYPOINT ["java","-jar","/bbaemin.jar"]
