package com.piecedonation.donation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger("Exception Handler");

    @ExceptionHandler
    public ResponseEntity<String> handle(final Exception e) {
        logger.error("Internal Error occurred", e);
        return ResponseEntity.internalServerError().body("내부 에러 발생" + e.getMessage());
    }
}

