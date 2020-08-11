FROM adoptopenjdk:11-hotspot
COPY target/*spring-boot-deamon.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]