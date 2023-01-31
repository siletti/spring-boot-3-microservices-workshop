package com.example.movieinfoservice.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String s) {
        super(s);
    }
}
