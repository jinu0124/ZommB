package com.ssafy.commb.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.commb.common.fcm.FcmService;
import com.ssafy.commb.dto.fcm.FcmDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.model.FirebaseToken;
import com.ssafy.commb.model.User;
import com.ssafy.commb.service.FollowService;
import com.ssafy.commb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping(value="/api/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private FcmService fcmService;

    @Autowired
    private UserService userService;

    // 팔로잉 하기
    @PostMapping(value="/{userId}")
    public ResponseEntity addFollowing(
            @PathVariable String userId,
            HttpServletRequest request) throws InterruptedException, FirebaseMessagingException, IOException {

        int follower = (int) request.getAttribute("userId");
        int following = Integer.parseInt(userId);

        followService.addFollowing(follower, following);

        UserDto.Response user = userService.getUserInfo(follower, request);

        List<FirebaseToken> firebaseTokens = fcmService.getUserToken(following);

        FcmDto fcm = FcmDto.builder()
                .message(FcmDto.Message.builder()
                        .token(firebaseTokens.get(0).getToken())
                        .notification(FcmDto.Notification.builder()
                                .title("follow")
                                .body(user.getData().getNickname())
                                .build())
                        .data(FcmDto.PayData.builder()
                                .userId(follower)
                                .nickname(user.getData().getNickname())
                                .userFileUrl(user.getData().getUserFileUrl())
                                .createAt(LocalDateTime.now(ZoneId.of("+9")))
                                .isRead(firebaseTokens.size() == 0 ? 0 : 1)
                                .targetUserId(following)
                                .build())
                        .build())
                .build();
//        fcmService.send(fcm);
//
        if(firebaseTokens.size() >= 1)  fcmService.sends(firebaseTokens, fcm);
        fcmService.savePushAlarm(fcm);

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
