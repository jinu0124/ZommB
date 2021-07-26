package com.ssafy.commb.controller;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.feed.CommentDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.feed.HashTagDto;
import com.ssafy.commb.dto.user.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.xml.stream.events.Comment;
import java.awt.print.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/feeds")
public class FeedController {

    // 게시물 리스트 검색
    @GetMapping("")
    public Object getFeeds(@RequestParam String searchWord){
        UserDto user = UserDto.builder().id(3).nickname("nick").userFileUrl("rehert").build();
        BookDto book = new BookDto();

        List<HashTagDto> hashTags = new ArrayList<>();
        hashTags.add(HashTagDto.builder().tag("hre").build());

        List<CommentDto> comments = new ArrayList<>();
        comments.add(CommentDto.builder().id(12).content("geww").userId(45).nickname("nick2").thumbCnt(560).createAt(new Date("2021-07-31")).isThumb(false).isMod(false).build());

        FeedDto feed = FeedDto.builder().id(1).createAt(new Date("2021-07-22")).content("few").isThumb(true)
                .thumbCnt(565).feedFileUrl("fwergrwe").user(user).book(book).hashTags(hashTags).comments(comments).build();

        FeedDto.Response feedRes = new FeedDto.Response();
        feedRes.setData(feed);

        List<FeedDto.Response> feedResList = new ArrayList<>();
        feedResList.add(feedRes);

        return new ResponseEntity<List<FeedDto.Response>>(feedResList, HttpStatus.OK);
    }

    // 게시물 작성
    @PostMapping("")
    public Object uploadFeed(@RequestBody Object feed){
        return null;
    }

    // 게시물 수정
    @PutMapping("/{feedId}")
    public Object modifyFeed(@RequestBody Object feed, @PathVariable String feedId){
        return null;
    }

    // 게시물 삭제
    @DeleteMapping("/{feedId}")
    public Object deleteFeed(@PathVariable String feedId){
        return null;
    }

    // 게시물 좋아요 or 취소
    @PostMapping("/{feedId}/feed-like")
    public Object likeFeed(@PathVariable String feedId){
        return null;
    }

    // 게시물 좋아요 목록
    @GetMapping("/{feedId}/feed-likes")
    public Object likeFeeds(@PathVariable String feedId){
        return null;
    }

    // 댓글 작성
    @PostMapping("/{feedId}/comments")
    public Object uploadComment(@PathVariable String feedId, @RequestBody Object feed){
        return null;
    }


    // 댓글 수정
    @PutMapping("/{feedId}/comments/{commentId}")
    public Object modifyComment(@PathVariable String commentId, @PathVariable String feedId, @RequestBody Object feed){
        return null;
    }

    // 댓글 삭제
    @DeleteMapping("/{feedId}/comments/{commentId}")
    public Object deleteComment(@PathVariable String commentId, @PathVariable String feedId){
        return null;
    }

    // 댓글 좋아요 or 취소
    @PostMapping("/{feedId}/comments/{commentId}/comment-like")
    public Object likeComment(@PathVariable String feedId, @PathVariable String commentId){
        return null;
    }


    // 댓글 좋아요 취소 삭제
//    @DeleteMapping("/{feedId}/comments/{commentId}/comment-like")
//    public Object deleteLikeComment(@PathVariable String feedId, @PathVariable String commentId){
//        return null;
//    }

    // 피드 신고
    @PostMapping("/{feedId}/reports")
    public Object reportFeed(@PathVariable String feedId, @RequestBody Object report){
        return null;
    }


}