FROM eclipse-temurin:17-jre-alpine
ADD target/news-producer*.jar news-producer.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","news-producer.jar"]