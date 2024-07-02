package com.toannguyen.news_consumer.listener;

import java.time.Instant;

public record News(String title, Instant createdOn) {
}