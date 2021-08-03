package com.ssafy.commb.controller;

import com.ssafy.commb.exception.ApplicationException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Test Controller
@RestController
@RequestMapping("/test")
public class TestController implements ErrorController {

    @GetMapping("/index")
    public String test(){
        throw new ApplicationException(HttpStatus.valueOf(204));

    }

    @GetMapping("/index2")
    public String test2(){
        throw new ApplicationException(HttpStatus.valueOf(403), "에러 메시지");
    }
}
