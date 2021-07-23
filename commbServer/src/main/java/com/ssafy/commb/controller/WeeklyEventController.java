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

    @GetMapping("/{weeklyId}/feeds")
    public Object findWeeklyEventFeedList(
            @PathVariable("weeklyId") Integer weeklyId
    ){
        return null;
    }

    @GetMapping("/{weeklyId}/users")
    public Object findWeeklyEventUserList(
            @PathVariable("weeklyId") Integer weeklyId
    ){
        return null;
    }

    @GetMapping("/{weeklyId}/users/cnt")
    public Object findWeeklyEventUserCnt(
            @PathVariable("weeklyId") Integer weeklyId
    ){
        return null;
    }
}
