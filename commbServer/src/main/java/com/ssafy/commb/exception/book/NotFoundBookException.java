package com.ssafy.commb.exception.book;

import com.ssafy.commb.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundBookException extends ApplicationException {

    private static final String DEFAULT_MESSAGE  = "존재하지 않는 책 입니다.";

    public NotFoundBookException(){
        super(HttpStatus.valueOf(404), DEFAULT_MESSAGE);
    }

}
