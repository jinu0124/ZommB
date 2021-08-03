package com.ssafy.commb.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationException extends RuntimeException{
    private static final long serialVersionUID = 123456789123456L;

    private Object obj;
    private HttpStatus status;

    public ApplicationException(HttpStatus status, String message, Object obj){
        super(message);
        this.obj = obj;
        this.status = status;
    }

    public ApplicationException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }

    public ApplicationException(HttpStatus status){
        this.status = status;
    }
}