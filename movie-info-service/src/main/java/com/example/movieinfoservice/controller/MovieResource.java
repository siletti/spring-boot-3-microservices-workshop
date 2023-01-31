package com.example.movieinfoservice.controller;


import com.example.movieinfoservice.clients.MovieClient;
import com.example.movieinfoservice.models.Movie;
import com.example.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    private final String apiKey;
    private final MovieClient movieClient;

    public MovieResource(MovieClient movieClient, @Value("${api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.movieClient = movieClient;
    }

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable String movieId) {

//
//        MovieSummary movieSummary = restTemplate.getForObject(
//                "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey,
//                MovieSummary.class);
        MovieSummary movieSummary = movieClient.getMovie(movieId, apiKey);
        return Movie.builder()
                .movieId(movieId)
                .name(movieSummary.title())
                .description(movieSummary.overview())
                .build();
    }

}
