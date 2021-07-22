package com.ssafy.commb.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/feeds")
public class FeedController {

    // 게시물 리스트 검색
    @GetMapping("/")
    public Object getFeeds(@RequestBody String searchWord){
        return null;
    }

    // 게시물 작성
    @PostMapping("/")
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

    // 댓글 좋아요
    @PostMapping("/{feedId}/comments/{commentId}/comment-like")
    public Object likeComment(@PathVariable String feedId, @PathVariable String commentId){
        return null;
    }


    // 댓글 삭제
    @DeleteMapping("/{feedId}/comments/{commentId}/comment-like")
    public Object deleteLikeComment(@PathVariable String feedId, @PathVariable String commentId){
        return null;
    }

    // 피드 신고
    @PostMapping("/{feedId}/reports")
    public Object reportFeed(@PathVariable String feedId, @RequestBody Object report){
        return null;
    }
}
