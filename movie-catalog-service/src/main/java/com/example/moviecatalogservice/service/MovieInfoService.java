package com.example.moviecatalogservice.service;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MovieInfoService {

    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "myProjectAllRemoteCallsCB", fallbackMethod = "getCatalogItemFallback")
    @Bulkhead(name = "movieInfoService", fallbackMethod = "getCatalogItemFallback")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.movieId(), Movie.class);
        return CatalogItem.builder()
                .name(movie.name())
                .desc(movie.description())
                .rate(rating.rating())
                .build();
    }

    public CatalogItem getCatalogItemFallback(Rating rating, NullPointerException exception) {
        return CatalogItem.builder()
                .name("Not Found movie Rating.id: " + rating.movieId())
                .desc("")
                .build();
    }

    public CatalogItem getCatalogItemFallback(Rating rating, Exception exception) {
        return CatalogItem.builder()
                .name("")
                .desc("")
                .build();
    }

}
