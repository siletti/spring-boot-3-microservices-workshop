package com.example.moviecatalogservice.models;

import lombok.Builder;

@Builder
public record CatalogItem(String name, String desc, int rate) {
}