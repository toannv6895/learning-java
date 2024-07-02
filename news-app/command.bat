@REM Run docker on docker-compose
docker compose up -d

@REM Run news-producer
./mvnw clean spring-boot:run

@REM Run news-consumer
./mvnw clean spring-boot:run

@REM call api
curl -i -X POST localhost:8080/api/news \
  -H 'Content-Type: application/json' \
  -d '{"title":"Article about Spring Cloud Stream and Kafka"}'

@REM Shutdown docker
docker compose down -v

@REM run unit test
./mvnw clean test