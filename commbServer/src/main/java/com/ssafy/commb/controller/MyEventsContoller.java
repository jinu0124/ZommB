package com.ssafy.commb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/my-events")
public class MyEventsContoller {
    @GetMapping("")
    public Object myEventList(){
        return null;
    }
}
