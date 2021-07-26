package com.ssafy.commb.controller;

import com.ssafy.commb.dto.book.KeywordDto;
import com.ssafy.commb.dto.event.DailyEventDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/daily-events")
public class DailyEventController {

    @GetMapping("")
    public ResponseEntity<List<DailyEventDto.Response>> findDailyEventList(){
        KeywordDto keywordDto = new KeywordDto();
        keywordDto.setId(15);
        DailyEventDto.Daily daily = new DailyEventDto.Daily(20);

        DailyEventDto dailyEvent = DailyEventDto.builder().daily(daily).keyword(keywordDto).build();

        DailyEventDto.Response dailyEventRes = new DailyEventDto.Response();
        dailyEventRes.setData(dailyEvent);

        List<DailyEventDto.Response> dailyEvents = new ArrayList<>();
        dailyEvents.add(dailyEventRes);

        return new ResponseEntity<List<DailyEventDto.Response>>(dailyEvents, HttpStatus.OK);
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
