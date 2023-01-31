package com.example.movieinfoservice.clients;

import com.example.movieinfoservice.config.FeignConfig;
import com.example.movieinfoservice.models.MovieSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-client", url = "${api.url}", configuration = {FeignConfig.class})
public interface MovieClient {

    @GetMapping("/{movieId}")
    MovieSummary getMovie(@PathVariable String movieId, @RequestParam(value = "api_key") String apiKey);
}
