package com.toannguyen.news_e2e_testing;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class AbstractTestcontainers {

    private static final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.1"));
    protected static final GenericContainer<?> newsProducerContainer = new GenericContainer<>("news-producer:latest");
    protected static final GenericContainer<?> newsConsumerContainer = new GenericContainer<>("news-consumer:latest");

    protected static final int NEWS_PRODUCER_EXPOSED_PORT = 8080;

    @DynamicPropertySource
    private static void dynamicProperties(DynamicPropertyRegistry registry) {
        Network network = Network.SHARED;

        kafkaContainer.withNetwork(network)
                .withNetworkAliases("kafka")
                .start();

        newsProducerContainer.withNetwork(network)
                .withNetworkAliases("news-producer")
                .withEnv("KAFKA_HOST", "kafka")
                .withEnv("KAFKA_PORT", "9092")
                .withExposedPorts(NEWS_PRODUCER_EXPOSED_PORT)
                .waitingFor(Wait.forLogMessage(".*Tomcat started on port.*", 1))
                .start();

        newsConsumerContainer.withNetwork(network)
                .withNetworkAliases("news-consumer")
                .withEnv("KAFKA_HOST", "kafka")
                .withEnv("KAFKA_PORT", "9092")
                .waitingFor(Wait.forLogMessage(".*Tomcat started on port.*", 1))
                .start();
    }
}