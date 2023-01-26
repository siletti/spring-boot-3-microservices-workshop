package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.UserRating;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class MovieCatalogResource {

    private final RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    @CircuitBreaker(name = "myProjectAllRemoteCallsCB", fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        UserRating forObject = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);

//            var movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/")
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();

        return forObject.userRating().parallelStream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.movieId(), Movie.class);

            return CatalogItem.builder()
                    .name(movie.name())
                    .desc(movie.description())
                    .rate(rating.rating())
                    .build();
        }).toList();
    }

    public List<CatalogItem> getFallbackCatalog(@PathVariable String userId, Exception e) {
        return List.of(CatalogItem.builder()
                .name("No movie")
                .desc("userid: " + userId)
                .rate(0)
                .build());
    }
}
