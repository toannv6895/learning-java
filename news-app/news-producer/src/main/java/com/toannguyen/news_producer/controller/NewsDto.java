package com.toannguyen.news_producer.controller;

import jakarta.validation.constraints.NotBlank;

public record NewsDto(@NotBlank String title) {
}
