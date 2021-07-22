package com.ssafy.commb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/follows")
public class FollowController {

    // 팔로잉 하기
    @PostMapping(value="/{userId}")
    public Object addFollowing(@PathVariable String userId){
        return null;
    }

    // 팔로잉 끊기
    @DeleteMapping(value="/{userId}")
    public Object deleteFollowing(@PathVariable String userId){
        return null;
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
