package com.toannguyen.news_producer.controller;

import com.toannguyen.news_producer.publisher.News;
import com.toannguyen.news_producer.publisher.NewsPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsPublisher newsPublisher;

    @Test
    void testPublishNewsWhenNewsDtoIsCorrectlyInformed() throws Exception {
        doNothing().when(newsPublisher).send(any(News.class));

        String content = "{\"title\":\"title test\"}";
        ResultActions resultActions = mockMvc.perform(post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print());

        resultActions.andExpect(status().isCreated());
    }

    @Test
    void testPublishNewsWhenNewsDtoIsIncorrectlyInformed() throws Exception {
        doNothing().when(newsPublisher).send(any(News.class));

        String content = "{}";
        ResultActions resultActions = mockMvc.perform(post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }
}
