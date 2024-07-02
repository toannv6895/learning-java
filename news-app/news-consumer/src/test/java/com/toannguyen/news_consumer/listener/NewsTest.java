package com.toannguyen.news_consumer.listener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class NewsTest {

    @Autowired
    private JacksonTester<News> jacksonTester;

    @Test
    void testSerialize() throws IOException {
        News news = new News("title test", Instant.parse("2023-10-14T12:56:04Z"));

        JsonContent<News> jsonContent = jacksonTester.write(news);

        assertThat(jsonContent)
                .hasJsonPathStringValue("@.title")
                .extractingJsonPathStringValue("@.title").isEqualTo("title test");
        assertThat(jsonContent)
                .hasJsonPathStringValue("@.createdOn")
                .extractingJsonPathStringValue("@.createdOn").isEqualTo("2023-10-14T12:56:04Z");
    }

    @Test
    void testDeserialize() throws IOException {
        String content = "{\"title\":\"title test\",\"createdOn\":\"2023-10-14T12:56:04Z\"}";

        News news = jacksonTester.parseObject(content);

        assertThat(news.title()).isEqualTo("title test");
        assertThat(news.createdOn()).isEqualTo("2023-10-14T12:56:04Z");
    }
}
