package com.toannguyen.news_producer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class NewsDtoTest {

    @Autowired
    private JacksonTester<NewsDto> jacksonTester;

    @Test
    void testSerialize() throws IOException {
        NewsDto newsDto = new NewsDto("title test");

        JsonContent<NewsDto> jsonContent = jacksonTester.write(newsDto);

        assertThat(jsonContent)
                .hasJsonPathStringValue("@.title")
                .extractingJsonPathStringValue("@.title").isEqualTo("title test");
    }

    @Test
    void testDeserialize() throws IOException {
        String content = "{\"title\":\"title test\"}";

        NewsDto newsDto = jacksonTester.parseObject(content);

        assertThat(newsDto.title()).isEqualTo("title test");
    }
}
