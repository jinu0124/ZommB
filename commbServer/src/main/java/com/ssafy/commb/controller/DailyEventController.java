package com.ssafy.commb.controller;

import com.ssafy.commb.dto.event.DailyEventDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Daily Event Function Controller
 */
@RestController
@RequestMapping(value="/api/daily-events")
public class DailyEventController {

    @Autowired
    private EventService eventService;

    @GetMapping("")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public ResponseEntity<DailyEventDto.Response> findDailyEventList(@RequestParam String today){
        DailyEventDto.Response dailyRes = eventService.keywordRecommend(today);

        return new ResponseEntity<DailyEventDto.Response>(dailyRes, HttpStatus.OK);
    }

    @GetMapping("/{dailyId}/feeds")
    public ResponseEntity<FeedDto.ResponseList> findDailyEventFeedList(@PathVariable("dailyId") Integer dailyId,
                                                                       @RequestParam Integer page,
                                                                       HttpServletRequest request){
        FeedDto.ResponseList feedResList = eventService.dailyFeeds(dailyId, page * 20, request);

        return new ResponseEntity<FeedDto.ResponseList>(feedResList, HttpStatus.OK);
    }

    @GetMapping("/{dailyId}/users")
    public ResponseEntity<MyDto.ResponseList> findDailyEventUserList(@PathVariable("dailyId") Integer dailyId,
                                                                     @RequestParam Integer page,
                                                                     HttpServletRequest request){
        MyDto.ResponseList myResList = eventService.getDailyParticipants(dailyId, page * 50, (int) request.getAttribute("userId"));

        return new ResponseEntity<MyDto.ResponseList>(myResList, HttpStatus.OK);
    }

    @GetMapping("/{dailyId}/users/cnt")
    public ResponseEntity<Map<String, Integer>> findDailyEventUserCnt(@PathVariable("dailyId") Integer dailyId)
    {
        int cnt = eventService.getDailyParticipantsCnt(dailyId);
        Map<String, Integer> map = new HashMap<>();
        map.put("participants", cnt);
        return ResponseEntity.ok().body(map);
    }

}
