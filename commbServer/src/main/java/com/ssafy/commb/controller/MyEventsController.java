package com.ssafy.commb.controller;

import com.ssafy.commb.dto.event.MyEventDto;
import com.ssafy.commb.dto.event.WeeklyEventDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/my-events")
@Api("MyEvent Controller API V1")
public class  MyEventsController {
    @GetMapping("/{userId}")
    @ApiOperation(value="내 이벤트 정보", response = MyEventDto.Response.class)
    public ResponseEntity<MyEventDto.Response> myEvent(@PathVariable Integer userId){

        List<WeeklyEventDto> weeklyEventDtoList = new ArrayList<>();

        WeeklyEventDto weeklyEvent = WeeklyEventDto.builder().bookFileUrl("https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F484319%3Ftimestamp%3D20201124225204").weeklyParticipate(true).build();
        weeklyEventDtoList.add(weeklyEvent);
        MyEventDto myEvent = MyEventDto.builder().id(9).bookmark(7).dailyParticipate(26).weekly(weeklyEventDtoList).build();


        MyEventDto.Response myEventRes = new MyEventDto.Response();
        myEventRes.setData(myEvent);

        return new ResponseEntity<MyEventDto.Response>(myEventRes, HttpStatus.OK);
    }
}
