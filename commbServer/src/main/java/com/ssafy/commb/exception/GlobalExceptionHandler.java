package com.ssafy.commb.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity handleApplicationException(ApplicationException e){

        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }

    // https://withseungryu.tistory.com/95
    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity handleDataAccessException(DataAccessException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e){
        return e.getMessage();
    }
}
