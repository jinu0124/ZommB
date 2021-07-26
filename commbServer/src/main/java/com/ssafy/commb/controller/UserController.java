package com.ssafy.commb.controller;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.user.MyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/users")
public class UserController {

    // 회원관리(관리자) - (관리자)회원 정보 리스트 검색
    @GetMapping("")
    public Object findUserList(){

        return null;
    }

    // 회원가입/로그인 - 자체 회원가입
    @PostMapping("")
    public Object singUp(){
        return null;
    }

    // 회원가입/로그인 - Email 중복 확인
    @GetMapping("/email")
    public Object duplicateEmail(){
        return null;
    }

    // 회원가입/로그인 - 소셜 회원가입
    @PostMapping("/social/kakao")
    public Object kakaoLogin(){
        return null;
    }

    // 회원가입/로그인 - 자체 로그인
    @GetMapping("/login")
    public ResponseEntity<MyDto.Response> login(){

        MyDto my = MyDto.builder().id(1).nickname("닉네임").userFileUrl("url").build();
        MyDto.Response myRes = new MyDto.Response();
        myRes.setData(my);

        return new ResponseEntity<MyDto.Response>(myRes, HttpStatus.OK);
    }

    // 회원가입/로그인 - 비밀번호 찾기
    @GetMapping("/{userId}")
    public Object findUser(
        @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 회원가입/로그인 - 프로필 수정
    @PostMapping("/{userId}")
    public Object updateUser(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 회원가입/로그인 - 비밀번호 변경
    @PatchMapping("/{userId}")
    public Object updateUserInfo(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 회원가입/로그인 - 회원 탈퇴
    @DeleteMapping("/{userId}")
    public Object deleteUser(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 회원 프로필 - 1인 게시물(피드) 리스트 조회
    @GetMapping("/{userId}/feeds")
    public Object findUserFeed(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 회원 프로필 - 게시물 수
    @GetMapping("/{userId}/feeds/cnt")
    public Object findUserFeedCnt(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 회원 프로필 - 읽은 책 목록(서재)
    @GetMapping("/{userId}/bookshelves")
    public Object findUserBookShelvesList(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // ?????????????
    @PostMapping("/{userId}/bookshelves")
    public Object insertUserBookShelves(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 회원 프로필 - 읽은/읽을 책(서재/북카드) 수
    @GetMapping("/{userId}/bookshelves/cnt")
    public Object findUserBookShelvesCnt(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 서재.북카트 - 책 삭제
    @DeleteMapping("/{userId}/bookshelves/{bookId}")
    public Object deleteUserBookShelf(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }

    // 서재/북카트 - 북카드에서 서재로 옮기기
    @PatchMapping("/{userId}/bookshelves/{bookId}")
    public Object updateUserBookShelf(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }

    // 서재/북카트 - 상단바 도서 목록 조회
    @GetMapping("/{userId}/top-bar")
    public Object findUserTopBar(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 서재/북카트 - PICK/Favorite/인생책 상단바에 등록
    @PostMapping("/{userId}/top-bar")
    public Object InsertUserTopBar(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 서재/북카트 - 상단바 도서 삭제
    @DeleteMapping("/{userId}/top-bar")
    public Object deleteUserTopBarAll(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 서재/북카드 - 상단바 도서 삭제
    @DeleteMapping("/{userId}/top-bar/{bookId}")
    public Object deleteUserTopBar(
            @PathVariable("userId") Integer userId,
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }

    // 검색 기능 - 친구 추천 목록
    @GetMapping("/{userId}/follow-recommend")
    public Object findFollowRecommend(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

    // 검색 기능 - 추천 키워드 목록
    @GetMapping("/{userId}/keyword-recommend")
    public Object findKeywordRecommend(
            @PathVariable("userId") Integer userId
    ){
        return null;
    }

}
