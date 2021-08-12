package com.ssafy.commb.controller;

import com.ssafy.commb.dto.event.WeeklyEventDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.service.EventService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Weekly Event Function Controller
 */
@RestController
@RequestMapping(value="/api/weekly-events")
public class WeeklyEventController {

    @Autowired
    private EventService eventService;

    @GetMapping("")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiOperation(value = "주간 이벤트 정보리스트")
    public ResponseEntity<WeeklyEventDto.Response> findWeeklyEventList(@RequestParam String today){
        WeeklyEventDto.Response weeklyEvent = eventService.bookRecommend(today);

        return ResponseEntity.ok().body(weeklyEvent);
    }

    @GetMapping("/{weeklyId}/feeds")
    @ApiOperation(value = "주간 이벤트에 대한 게시물 리스트")
    public ResponseEntity<FeedDto.ResponseList> findWeeklyEventFeedList(
            @PathVariable("weeklyId") Integer weeklyId,
            HttpServletRequest request
    ){
        FeedDto.ResponseList feedRes = eventService.weeklyFeeds(weeklyId, request);

        return ResponseEntity.ok().body(feedRes);
    }

    @GetMapping("/{weeklyId}/users/cnt")

    public ResponseEntity<Map<String, Integer>> findWeeklyEventUserCnt(
            @PathVariable("weeklyId") Integer weeklyId
    ){
        Map<String, Integer> map = new HashMap<>();
        int participants = eventService.getWeeklyParticipantsCnt(weeklyId);
        map.put("participants", participants);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/{weeklyId}/users")
    @ApiOperation(value = "주간 이벤트 참여자 목록")
    public ResponseEntity<MyDto.ResponseList> findWeeklyEventUserList(
            @PathVariable("weeklyId") Integer weeklyId,
            HttpServletRequest request
    ){
        MyDto.ResponseList myResList = eventService.getWeeklyParticipants(weeklyId, request);

        return ResponseEntity.ok().body(myResList);
    }
}
