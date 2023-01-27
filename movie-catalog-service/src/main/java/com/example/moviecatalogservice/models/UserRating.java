package com.example.moviecatalogservice.models;

import lombok.Builder;

import java.util.List;

@Builder
public record UserRating(String userId, List<Rating> userRating) {
}
