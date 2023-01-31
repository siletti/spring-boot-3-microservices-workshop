package com.example.moviecatalogservice.service;

import com.example.moviecatalogservice.models.UserRating;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRatingService {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "myProjectAllRemoteCallsCB", fallbackMethod = "getUserRatingFallback")
    @Bulkhead(name = "userRatingService", fallbackMethod = "getCatalogItemFallback")
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    public UserRating getUserRatingFallback(String userId, Exception exception) {
        return UserRating.builder()
                .userId(userId)
                .userRating(List.of())
                .build();
    }
}
