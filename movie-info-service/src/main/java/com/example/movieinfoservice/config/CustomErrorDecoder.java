package com.example.movieinfoservice.config;

import com.example.movieinfoservice.exceptions.MovieNotFoundException;
import com.example.movieinfoservice.exceptions.MovieServiceNotAvailableException;
import com.example.movieinfoservice.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 401 -> new UnauthorizedException("Unauthorized api key");
            case 404 -> new MovieNotFoundException("Movie not found");
            case 503 -> new MovieServiceNotAvailableException("Movie Api is unavailable");
            default -> new Exception("Exception while getting Movie details");
        };
    }

}
