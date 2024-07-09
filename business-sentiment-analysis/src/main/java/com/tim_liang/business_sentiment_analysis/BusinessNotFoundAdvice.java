package com.tim_liang.business_sentiment_analysis;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class BusinessNotFoundAdvice {

    @ExceptionHandler(BusinessNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String businessNotFoundHandler(BusinessNotFoundException ex) {
        return ex.getMessage();
    }
}
