FROM eclipse-temurin:17-jre-alpine
ADD target/news-consumer*.jar news-consumer.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","news-consumer.jar"]