FROM openjdk:17
EXPOSE 8080
ADD target/zoo-be.jar zoo-be.jar
ENTRYPOINT ["java", "-jar", "/zoo-be.jar"]