package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        UserRating forObject = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);

        return forObject.userRating().parallelStream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.movieId(), Movie.class);

//            var movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/")
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();

            return CatalogItem.builder()
                    .name(movie.name())
                    .desc("desccccccc")
                    .rate(rating.rating())
                    .build();
        }).toList();
    }
}
