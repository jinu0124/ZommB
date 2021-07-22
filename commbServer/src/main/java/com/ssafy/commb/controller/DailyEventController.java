package com.ssafy.commb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/daily-events")
public class DailyEventController {

    @GetMapping("")
    public Object findWeeklyEventList(){
        return null;
    }

    @GetMapping("/{bookId}/feeds")
    public Object findDailyEventFeedList(
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }

    @GetMapping("/{bookId}/users")
    public Object findDailyEventUserList(
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }
}
