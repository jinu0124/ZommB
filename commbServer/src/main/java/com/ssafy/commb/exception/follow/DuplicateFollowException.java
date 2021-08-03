package com.ssafy.commb.exception.follow;

import com.ssafy.commb.exception.ApplicationException;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;

public class DuplicateFollowException extends ApplicationException {

    private static final String DEFAULT_MESSAGE  = "이미 팔로우한 상대입니다.";

    public DuplicateFollowException(){
        super(HttpStatus.valueOf(400), DEFAULT_MESSAGE);
    }
}
