package com.example.movieinfoservice.exceptions;

public class MovieServiceNotAvailableException extends RuntimeException {
    public MovieServiceNotAvailableException(String s) {
        super(s);
    }
}
