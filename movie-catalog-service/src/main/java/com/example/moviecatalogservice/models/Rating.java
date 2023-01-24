package com.example.moviecatalogservice.models;

import lombok.Builder;

@Builder
public record Rating(String movieId, int rating) {
}
