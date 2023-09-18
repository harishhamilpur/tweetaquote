package com.example.AutomateTweet.services;

import com.example.AutomateTweet.models.TweetModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class GenerateTweet {

    private static RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(GenerateTweet.class);
    private StringBuilder tweet;
    @Scheduled(cron = "30 5 * * * *")
    @Retryable(value = {RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 300000))
    public void getTweet() {

        try{
            ResponseEntity<TweetModel[]> response = restTemplate.getForEntity("https://zenquotes.io/api/random", TweetModel[].class);
            if(response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                TweetModel[] body = response.getBody();
                formatTheTweet(body[0].getQ(), body[0].getA()).postTheTweet();
            }
        } catch(RestClientException e){ logger.error("Error occurred during getting a quote from zen quotes API", e);
        } catch(Exception e) {logger.error("Unexpected error occurred", e);
        }
    }

    public GenerateTweet formatTheTweet(String quote, String author) {
        this.tweet = new StringBuilder(String.format("\"%s\" — ", quote.trim()));
        this.tweet.append(author.trim());
        logger.info("Tweet received from zen quotes API ", tweet);
        return this;
    }


    public void postTheTweet() {

    }
}
