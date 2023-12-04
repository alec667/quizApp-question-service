package com.example.questionsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuestionExceptionHandler {

    @ExceptionHandler(value = QuestionNotFoundException.class)
    public ResponseEntity<Object> questionNotFound(QuestionNotFoundException questionNotFoundException) {
        return new ResponseEntity<>(questionNotFoundException.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}
