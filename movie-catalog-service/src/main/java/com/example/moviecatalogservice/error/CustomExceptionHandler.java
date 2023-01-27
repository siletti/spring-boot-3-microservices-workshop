package com.example.moviecatalogservice.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    private static class ResponseEntityBuilder {
        public static ResponseEntity<ApiError> build(ApiError apiError) {
            return new ResponseEntity<>(apiError, apiError.status());
        }
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handleAllException(final Exception ex, WebRequest request) {
        log.error("Internal Server Error Exception occurred.", ex);
        return ResponseEntityBuilder.build(ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .detail(request.getDescription(false))
                .build());
    }
}
