package com.example.moviecatalogservice.models;

import lombok.Builder;


@Builder
public record Movie(String movieId, String name) {
}
