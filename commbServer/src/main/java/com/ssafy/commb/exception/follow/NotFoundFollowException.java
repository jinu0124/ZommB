package com.ssafy.commb.exception.follow;

import com.ssafy.commb.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotFoundFollowException extends ApplicationException {

    private static final String DEFAULT_MESSAGE  = "존재하지 않는 팔로우 입니다.";

    public NotFoundFollowException(){
        super(HttpStatus.valueOf(404), DEFAULT_MESSAGE);
    }

}
