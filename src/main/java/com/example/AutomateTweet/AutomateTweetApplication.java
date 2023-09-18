package com.example.AutomateTweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRetry
public class AutomateTweetApplication {

	public static void main(String[] args) {

		SpringApplication.run(AutomateTweetApplication.class, args);
	}

}
