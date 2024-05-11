FROM openjdk:11
ARG JAR_FILE=build/libs/Hallym-Festival-2024-BackEnd-0.0.1-SNAPSHOT.jar
ARG PROFILES
ARG ENV
ARG PORT
ARG SERVER_NAME
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-Dserver.port=${PORT}", "-DserverName=${SERVER_NAME}","-jar", "app.jar"]