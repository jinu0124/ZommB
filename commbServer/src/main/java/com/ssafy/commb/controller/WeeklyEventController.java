package com.ssafy.commb.controller;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.ssafy.commb.dto.event.WeeklyEventDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/weekly-events")
public class WeeklyEventController {

    @Autowired
    private EventService eventService;

    // 책 추천
    @GetMapping("")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public ResponseEntity<WeeklyEventDto.Response> findWeeklyEventList(@RequestParam LocalDate today){
//        LocalDate localDate = LocalDate.now(ZoneId.of("+9"));
        WeeklyEventDto.Response weeklyEvent = eventService.bookRecommend(today);

        return ResponseEntity.ok().body(weeklyEvent);
    }

    // 주간 이벤트에 대한 게시물 리스트
    @GetMapping("/{weeklyId}/feeds")
    public ResponseEntity<FeedDto.ResponseList> findWeeklyEventFeedList(
            @PathVariable("weeklyId") Integer weeklyId,
            HttpServletRequest request
    ){
        FeedDto.ResponseList feedRes = eventService.weeklyFeeds(weeklyId, request);

        return ResponseEntity.ok().body(feedRes);
    }

    // 주간 이벤트 참여자 수
    @GetMapping("/{weeklyId}/users/cnt")
    public ResponseEntity<Map<String, Integer>> findWeeklyEventUserCnt(
            @PathVariable("weeklyId") Integer weeklyId
    ){
        Map<String, Integer> map = new HashMap<>();
        int participants = eventService.getWeeklyParticipantsCnt(weeklyId);
        map.put("participants", participants);

        return ResponseEntity.ok().body(map);
    }

    // 주간 이벤트 참여자 목록
    @GetMapping("/{weeklyId}/users")
    public ResponseEntity<MyDto.ResponseList> findWeeklyEventUserList(
            @PathVariable("weeklyId") Integer weeklyId,
            HttpServletRequest request
    ){
        MyDto.ResponseList myResList = eventService.getWeeklyParticipants(weeklyId, request);
        return ResponseEntity.ok().body(myResList);
    }
}
