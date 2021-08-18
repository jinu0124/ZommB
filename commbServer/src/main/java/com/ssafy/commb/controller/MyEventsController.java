package com.ssafy.commb.controller;

import com.ssafy.commb.dto.event.MyEventDto;
import com.ssafy.commb.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ My Event Info
 */
@RestController
@RequestMapping(value="/api/my-events")
@Api("MyEvent Controller API V1")
public class  MyEventsController {

    @Autowired
    private EventService eventService;

    // 내 이벤트 정보
    @GetMapping("/{userId}")
    @ApiOperation(value="내 이벤트 정보", response = MyEventDto.Response.class)
    public ResponseEntity<MyEventDto.Response> myEvent(@PathVariable Integer userId,
                                                       HttpServletRequest request){

        MyEventDto.Response myRes = eventService.getMyEvent(request);

        return new ResponseEntity<MyEventDto.Response>(myRes, HttpStatus.OK);
    }
}



