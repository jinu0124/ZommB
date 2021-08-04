package com.ssafy.commb.controller;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value="/follows")
public class FollowController {

    @Autowired
    FollowService followService;

    // 팔로잉 하기
    @PostMapping(value="/{userId}")
    public ResponseEntity addFollowing(
            @PathVariable String userId,
            HttpServletRequest request){

        int follower = (int) request.getAttribute("userId");
        int following = Integer.parseInt(userId);

        followService.addFollowing(follower, following);

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 팔로잉 끊기
    @DeleteMapping(value="/{userId}")
    public Object deleteFollowing(
            @PathVariable String userId,
            HttpServletRequest request){

        int follower = (int) request.getAttribute("userId");
        int following = Integer.parseInt(userId);

        followService.deleteFollowing(follower, following);

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 팔로잉 목록
    @GetMapping(value="/{userId}/following")
    public Object getFollowings(
            @PathVariable int userId,
            HttpServletRequest request){

        int meId = (int) request.getAttribute("userId");
        List<MyDto> myDtoList = followService.getFollowings(meId, userId);
        MyDto.ResponseList myRes = MyDto.ResponseList.builder()
                .data(myDtoList)
                .build();


        return new ResponseEntity<MyDto.ResponseList>(myRes, HttpStatus.valueOf(200));
    }

    // 팔로워 목록
    @GetMapping(value="/{userId}/follower")
    public Object getFollowers(
            @PathVariable int userId,
            HttpServletRequest request) {

        int meId = (int) request.getAttribute("userId");
        List<MyDto> myDtoList = followService.getFollowers(meId, userId);
        MyDto.ResponseList myRes = MyDto.ResponseList.builder()
                .data(myDtoList)
                .build();


        return new ResponseEntity<MyDto.ResponseList>(myRes, HttpStatus.valueOf(200));
    }

    // userId 회원의 피드 리스트
    @GetMapping(value="/{userId}/following/feeds")
    public Object feeds(@PathVariable String userId){
        return null;
    }
}
