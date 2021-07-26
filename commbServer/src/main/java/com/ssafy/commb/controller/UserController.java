package com.ssafy.commb.controller;

import com.ssafy.commb.dto.user.MyDto;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping(value="/users")
public class UserController {

    static final int id = 1;
    static final String nickname = "크루엘라";
    static final String url = "URL 자리";
    static final String tag = "#오늘도 힘내세요";
    static final String content = "이것은 글입니다.";
    static final int cnt = 134;
    static Date date;
    static final boolean bool = true;
    static final String name = "트럼프";
    static final String genre = "공포";
    static final int year = 2021;
    static final float rate = 3.5f;

    static {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").parse("2021-07-31 10:12:15");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("")
    public Object findUserList(){


        return null;
    }

    @PostMapping("")
    public Object singUp(){
        return null;
    }

    @GetMapping("/email")
    public Object duplicateEmail(){
        return null;
    }

    @PostMapping("/social/kakao")
    public Object kakaoLogin(){
        return null;
    }

    @GetMapping("/login")
    public ResponseEntity<MyDto.Response> login(){

        MyDto my = MyDto.builder().id(1).nickname("닉네임").userFileUrl("url").build();
        MyDto.Response myRes = new MyDto.Response();
        myRes.setData(my);

        return new ResponseEntity<MyDto.Response>(myRes, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public Object findUser(
        @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @PostMapping("/{userId}")
    public Object updateUser(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @PatchMapping("/{userId}")
    public Object updateUserInfo(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @DeleteMapping("/{userId}")
    public Object deleteUser(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @GetMapping("/{userId}/feeds")
    public Object findUserFeed(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @GetMapping("/{userId}/feeds/cnt")
    public Object findUserFeedCnt(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @GetMapping("/{userId}/bookshelves")
    public Object findUserBookShelvesList(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }
    @PostMapping("/{userId}/bookshelves")
    public Object insertUserBookShelves(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @GetMapping("/{userId}/bookshelves/cnt")
    public Object findUserBookShelvesCnt(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @DeleteMapping("/{userId}/bookshelves/{bookId}")
    public Object deleteUserBookShelf(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }

    @PatchMapping("/{userId}/bookshelves/{bookId}")
    public Object updateUserBookShelf(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }

    @GetMapping("/{userId}/top-bar")
    public Object findUserTopBar(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @PostMapping("/{userId}/top-bar")
    public Object InsertUserTopBar(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @DeleteMapping("/{userId}/top-bar")
    public Object deleteUserTopBarAll(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @DeleteMapping("/{userId}/top-bar/{bookId}")
    public Object deleteUserTopBar(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }

    @GetMapping("/{userId}/follow-recommend")
    public Object findFollowRecommend(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    @GetMapping("/{userId}/keyword-recommend")
    public Object findKeywordRecommend(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

}
