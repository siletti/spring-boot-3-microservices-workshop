package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.service.MovieInfoService;
import com.example.moviecatalogservice.service.UserRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class MovieCatalogResource {

    private final MovieInfoService movieInfoService;
    private final UserRatingService userRatingService;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        return userRatingService.getUserRating(userId)
                .userRating()
                .parallelStream()
                .map(movieInfoService::getCatalogItem)
                .toList();
    }

}
