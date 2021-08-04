package com.ssafy.commb.exception.user;

import com.ssafy.commb.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundUserException extends ApplicationException {

    private static final String DEFAULT_MESSAGE  = "존재하지 않는 사용자입니다.";

    public NotFoundUserException() { super(HttpStatus.valueOf(404), DEFAULT_MESSAGE);}
}
