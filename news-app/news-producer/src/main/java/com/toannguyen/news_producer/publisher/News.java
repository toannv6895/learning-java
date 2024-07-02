package com.toannguyen.news_producer.publisher;

import java.time.Instant;

public record News(String title, Instant createdOn) {
}
