package com.example.movieinfoservice.models;

import lombok.Builder;

@Builder
public record MovieSummary(String id, String title, String overview) {
}
