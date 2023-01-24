package com.example.moviecatalogservice.models;

import lombok.Builder;

import java.util.List;

@Builder
public record UserRating(List<Rating> userRating) {
}
