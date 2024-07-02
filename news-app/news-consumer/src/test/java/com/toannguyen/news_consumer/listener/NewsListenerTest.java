package com.toannguyen.news_consumer.listener;

import com.toannguyen.news_consumer.NewsConsumerApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class NewsListenerTest {

    @Test
    void testListenNews(CapturedOutput output) {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration
                        .getCompleteConfiguration(NewsConsumerApplication.class))
                .web(WebApplicationType.NONE)
                .run()) {
            String title = "title test";
            String createdOn = "2023-10-14T12:56:04Z";

            News news = new News(title, Instant.parse(createdOn));
            Message<News> newsMessage = MessageBuilder.withPayload(news).build();

            InputDestination inputDestination = context.getBean(InputDestination.class);
            inputDestination.send(newsMessage, "com.example.news-producer.news");

            String expected = "Received News! \"%s\" created on '%s'".formatted(title, createdOn);
            assertThat(output).contains(expected);
        }
    }
}
