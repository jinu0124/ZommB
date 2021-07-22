package com.ssafy.commb.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/users")
public class UserController {

    @GetMapping("")
    public Object finUserList(){
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
    public Object login(){
        return null;
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
