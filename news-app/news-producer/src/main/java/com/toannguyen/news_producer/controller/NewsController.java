package com.toannguyen.news_producer.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.toannguyen.news_producer.publisher.News;
import com.toannguyen.news_producer.publisher.NewsPublisher;

import java.time.Instant;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsPublisher newsPublisher;

    public NewsController(NewsPublisher newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void publishNews(@Valid @RequestBody NewsDto newsDto) {
        newsPublisher.send(new News(newsDto.title(), Instant.now()));
    }
}
