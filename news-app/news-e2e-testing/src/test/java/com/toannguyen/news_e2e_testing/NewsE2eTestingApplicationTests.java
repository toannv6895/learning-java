package com.toannguyen.news_e2e_testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.output.OutputFrame.OutputType;
import org.testcontainers.containers.output.WaitingConsumer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NewsE2eTestingApplicationTests extends AbstractTestcontainers {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void testPublishNews() {
		WaitingConsumer waitingConsumer = new WaitingConsumer();
		newsConsumerContainer.followOutput(waitingConsumer, OutputType.STDOUT);

		String newsProducerApiUrl = "http://localhost:%s/api/news".formatted(newsProducerContainer.getMappedPort(NEWS_PRODUCER_EXPOSED_PORT));
		NewsDto newsDto = new NewsDto("News Test 123456 ABCDEF");

		ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity(newsProducerApiUrl, newsDto, Void.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		try {
			waitingConsumer.waitUntil(frame ->
					frame.getUtf8String().contains("Received News! \"News Test 123456 ABCDEF\""), 5, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			fail("The expected message was not received");
		}
	}

	private record NewsDto(String title) {
	}
}
