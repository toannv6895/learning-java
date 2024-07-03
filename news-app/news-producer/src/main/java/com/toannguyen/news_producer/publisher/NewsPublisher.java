package com.toannguyen.news_producer.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class NewsPublisher {

    private static final Logger log = LoggerFactory.getLogger(NewsPublisher.class);

    private final StreamBridge streamBridge;

    public NewsPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void send(News news) {
        Message<News> newsMessage = CloudEventMessageBuilder.withData(news)
                .setSource("news-app/news-producer")
                .build();
        streamBridge.send("news-out-0", newsMessage);
        log.info("{} sent!", news);
    }
}
