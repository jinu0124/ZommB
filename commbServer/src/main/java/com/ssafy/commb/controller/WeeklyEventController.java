package com.ssafy.commb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/weekly-events")
public class WeeklyEventController {

    @GetMapping("")
    public Object findWeeklyEventList(){
        return null;
    }

    @GetMapping("/{bookId}/feeds")
    public Object findWeeklyEventFeedList(
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }

    @GetMapping("/{bookId}/users")
    public Object findWeeklyEventUserList(
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }
}
