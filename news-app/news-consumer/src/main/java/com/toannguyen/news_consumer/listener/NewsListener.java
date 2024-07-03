package com.toannguyen.news_consumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class NewsListener {

    private static final Logger log = LoggerFactory.getLogger(NewsListener.class);

    @Bean
    public Consumer<Message<News>> news() {
        return news -> {
            log.info("Received News! \"{}\" created on '{}'. Headers: {}", news.getPayload().title(), news.getPayload().createdOn(),news.getHeaders());
        };
    }
}