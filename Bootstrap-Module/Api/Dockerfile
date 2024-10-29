FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=Bootstrap-Module/Api/build/libs/Api.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app.jar"]