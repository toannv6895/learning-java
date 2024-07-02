package com.toannguyen.news_e2e_testing;

import org.springframework.boot.SpringApplication;

public class TestNewsE2eTestingApplication {

	public static void main(String[] args) {
		SpringApplication.from(NewsE2eTestingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
