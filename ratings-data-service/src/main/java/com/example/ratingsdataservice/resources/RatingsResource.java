package com.example.ratingsdataservice.resources;

import com.example.ratingsdataservice.models.Rating;
import com.example.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 88);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRatings(@PathVariable String userId) {
        return UserRating.builder()
                .userRating(List.of(
                        Rating.builder().movieId("1234").rating(4).build(),
                        Rating.builder().movieId("5678").rating(3).build()
                ))
                .build();

    }
}
