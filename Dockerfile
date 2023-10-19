FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/zoo-be.jar zoo-be.jar
ENTRYPOINT ["java", "-jar", "/zoo-be.jar"]
