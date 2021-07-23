package com.ssafy.commb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/daily-events")
public class DailyEventController {

    @GetMapping("")
    public Object findDailyEventList(){
        return null;
    }

    @GetMapping("/{dailyId}/feeds")
    public Object findDailyEventFeedList(
            @PathVariable("dailyId") Integer dailyId
    ){
        return null;
    }

    @GetMapping("/{dailyId}/users")
    public Object findDailyEventUserList(
            @PathVariable("dailyId") Integer dailyId
    ){
        return null;
    }

    @GetMapping("/{dailyId}/users/cnt")
    public Object findDailyEventUserCnt(
            @PathVariable("dailyId") Integer dailyId
    ){
        return null;
    }
}
