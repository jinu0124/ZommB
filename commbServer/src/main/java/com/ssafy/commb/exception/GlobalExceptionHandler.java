package com.ssafy.commb.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Global Exception Handler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

        /**
         * @param e : 던져오는 Exception   Map<HttpStatus, Object, String>
         * @return : ResponseEntity
         */
    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity handleApplicationException(ApplicationException e){
        e.printStackTrace();
        if(e.getObj() != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("data", e.getObj());
            return ResponseEntity.status(e.getStatus()).body(map);
        }
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    // https://withseungryu.tistory.com/95
    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity handleDataAccessException(DataAccessException e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e){
        e.printStackTrace();
        return e.getMessage();
    }
}
