package com.example.movieinfoservice.models;

import lombok.Builder;


@Builder
public record Movie(String movieId, String name) {
}
