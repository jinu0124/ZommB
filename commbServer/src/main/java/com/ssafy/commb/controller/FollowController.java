package com.ssafy.commb.controller;

import com.ssafy.commb.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

        int follower = Integer.parseInt((String) request.getAttribute("userId"));
        int following = Integer.parseInt(userId);
        try {
            followService.addFollowing(follower, following);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.valueOf(400));
        }

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 팔로잉 끊기
    @DeleteMapping(value="/{userId}")
    public Object deleteFollowing(
            @PathVariable String userId,
            HttpServletRequest request){

        int follower = Integer.parseInt((String) request.getAttribute("userId"));
        int following = Integer.parseInt(userId);

        followService.deleteFollowing(follower, following);

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 팔로잉 목록
    @GetMapping(value="/{userId}/following")
    public Object getFollowings(@PathVariable String userId){
        return null;
    }

    // 팔로워 목록
    @GetMapping(value="/{userId}/follower")
    public Object getFollowers(@PathVariable String userId){
        return null;
    }

    // userId 회원의 피드 리스트
    @GetMapping(value="/{userId}/following/feeds")
    public Object feeds(@PathVariable String userId){
        return null;
    }
}
