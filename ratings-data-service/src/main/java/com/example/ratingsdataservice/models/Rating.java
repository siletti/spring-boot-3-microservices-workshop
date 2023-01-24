package com.example.ratingsdataservice.models;

import lombok.Builder;

@Builder
public record Rating(String movieId, int rating) {
}
