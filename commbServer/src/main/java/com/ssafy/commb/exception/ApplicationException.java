package com.ssafy.commb.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationException extends RuntimeException{
    private static final long serialVersionUID = 123456789123456L;

    private Object msg;
    private HttpStatus status;

    public ApplicationException(HttpStatus status){
        this.status = status;
    }
}
