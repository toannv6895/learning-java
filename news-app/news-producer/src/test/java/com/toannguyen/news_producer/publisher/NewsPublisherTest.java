package com.toannguyen.news_producer.publisher;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class NewsPublisherTest {

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private NewsPublisher newsPublisher;

    @Test
    void testSendNews() throws JSONException {
        String title = "title test";
        String createdOn = "2023-10-14T12:56:04.147095Z";

        newsPublisher.send(new News(title, Instant.parse(createdOn)));

        Message<byte[]> outputMessage = outputDestination.receive(0, "com.example.news-producer.news");

        assertThat(outputMessage).isNotNull();

        MessageHeaders headers = outputMessage.getHeaders();
        assertThat(headers.get(MessageHeaders.CONTENT_TYPE)).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

        JSONObject payloadJson = new JSONObject(new String(outputMessage.getPayload()));
        assertThat(payloadJson.getString("title")).isEqualTo(title);
        assertThat(payloadJson.getString("createdOn")).isEqualTo(createdOn);
    }
}
