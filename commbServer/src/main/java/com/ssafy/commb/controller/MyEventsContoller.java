package com.ssafy.commb.controller;

import com.ssafy.commb.dto.event.MyEventDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value="/my-events")
public class MyEventsContoller {
    @GetMapping("")
    public ResponseEntity<List<MyEventDto, MyEventDto.Response>> myEventList(){

        MyEventDto myEvent = MyEventDto.builder().id(9).bookmark(7).dailyParticipate(26).build();

        MyEventDto.Response myEventRes = new MyEventDto.Response();
        myEventRes.setData(myEvent);

        List<MyEventDto.Response> myEvents = new ArrayList<>();
        myEvents.add(myEventRes);

        return new ResponseEntity<List<MyEventDto, MyEventDto.Response>>(myEvents, HttpStatus.OK);
    }
}
