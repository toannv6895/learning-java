package com.toannguyen.news_producer.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class NewsPublisher {

    private static final Logger log = LoggerFactory.getLogger(NewsPublisher.class);

    private final StreamBridge streamBridge;

    public NewsPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void send(News news) {
        streamBridge.send("news-out-0", news);
        log.info("{} sent!", news);
    }
}
