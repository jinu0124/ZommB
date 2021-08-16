package com.ssafy.commb.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.commb.common.fcm.FcmService;
import com.ssafy.commb.dto.fcm.FcmDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.FirebaseToken;
import com.ssafy.commb.service.CommentService;
import com.ssafy.commb.service.FeedService;
import com.ssafy.commb.service.ThumbService;
import com.ssafy.commb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/feeds")
@Api("Feed Controller API V1")
public class FeedController {

    // Dummy Data Set----------------------------------------------------------------------------
    static final int id = 1;
    static final String nickname = "크루엘라";
    static final String url = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F484319%3Ftimestamp%3D20201124225204";
    static final String tag = "#오늘도 힘내세요";
    static final String content = "이것은 글입니다.";
    static final int cnt = 134;
    static Date date;
    static final boolean bool = true;

    static {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").parse("2021-07-31 10:12:15");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    // Dummy Data Set----------------------------------------------------------------------------

    @Autowired
    private FeedService feedService;

    @Autowired
    private ThumbService thumbService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FcmService fcmService;

    @Autowired
    private UserService userService;

    // /feeds?searchWord="abc"
    // 게시물 리스트 검색
    @GetMapping("")
    @ApiOperation(value = "피드 리스트 검색", response = FeedDto.Response.class)
    public ResponseEntity<FeedDto.ResponseList> getFeeds(@RequestParam String searchWord,
                                                         @RequestParam Integer page,
                                                         HttpServletRequest request) {

        int userId = (Integer) request.getAttribute("userId");

        FeedDto.ResponseList feedResList = feedService.getFeeds(searchWord, page * 20, userId);

        return new ResponseEntity<FeedDto.ResponseList>(feedResList, HttpStatus.OK);
    }

    // 게시물 작성
    @PostMapping("")
    @ApiOperation(value = "피드 작성")
    // @RequestBody 는 Json type으로 들어오는 객체를 파싱하는 역할 → formData 형식에서는 사용 X , swagger에서 MultipartHttpServletRequest 사용 X
    public ResponseEntity uploadFeed(FeedDto.RegisterRequest feedReq, MultipartHttpServletRequest request) throws IOException, ServletException {

        feedService.uploadFeed(feedReq, request);

        return ResponseEntity.ok().build();
    }

    // 게시물 수정
    @PutMapping("/{feedId}")
    @ApiOperation(value = "피드 수정")
    public ResponseEntity modifyFeed(@RequestBody String content, @PathVariable Integer feedId, HttpServletRequest request) {

        if (content == null) return ResponseEntity.status(400).build();

        int myUserId = (Integer) request.getAttribute("userId");
        int userId = feedService.getUserId(feedId);

        if (myUserId != userId)
            throw new ApplicationException(HttpStatus.valueOf(403), "게시물 수정 권한 없음"); // 작성자한테만 수정 버튼 보이도록 front에서 막기

        feedService.modifyFeed(content, feedId);

        return new ResponseEntity(HttpStatus.valueOf(200));
    }

    // 게시물 삭제
    @DeleteMapping("/{feedId}")
    @ApiOperation(value = "피드 삭제")
    public ResponseEntity deleteFeed(@PathVariable Integer feedId, HttpServletRequest request) {

        int myUserId = (Integer) request.getAttribute("userId");
        int userId = feedService.getUserId(feedId);

        if (myUserId != userId)
            throw new ApplicationException(HttpStatus.valueOf(403), "게시물 삭제 권한 없음"); // 작성자한테만 삭제 버튼 보이도록 front에서 막기

        feedService.deleteFeed(feedId);

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 게시물 좋아요
    @PostMapping("/{feedId}/feed-like")
    @ApiOperation(value = "피드 좋아요 누르기")
    public ResponseEntity likeFeed(@PathVariable Integer feedId, HttpServletRequest request) throws InterruptedException, FirebaseMessagingException, IOException {

        int userId = (Integer) request.getAttribute("userId");

        thumbService.likeFeed(feedId, userId);

        List<FirebaseToken> firebaseToken = fcmService.getUserToken(feedService.getUserId(feedId));

        UserDto.Response user = userService.getUserInfo(userId, request);
        FeedDto feed = feedService.getFeedInfo(feedId);

        FcmDto fcm = FcmDto.builder()
                .message(FcmDto.Message.builder()
                        .data(FcmDto.PayData.builder()
                                .userId(userId)
                                .nickname(user.getData().getNickname())
                                .feedId(feed.getId())
                                .feedFileUrl(feed.getFeedFileUrl())
                                .content(feed.getContent())
                                .targetUserId(feed.getUser().getId())
                                .createAt(LocalDateTime.now(ZoneId.of("+9")))
                                .isRead(firebaseToken.size() == 0 ? 0 : 1)
                                .build())
                        .notification(FcmDto.Notification.builder()
                                .title("like")
                                .body("")
                                .build())
                        .build())
                .build();

        if(firebaseToken.size() >= 1) fcmService.sends(firebaseToken, fcm);
        fcmService.savePushAlarm(fcm);

        return new ResponseEntity(HttpStatus.valueOf(201));
    }


    // 게시물 좋아요 취소
    @DeleteMapping("/{feedId}/feed-like")
    @ApiOperation(value = "피드 좋아요 취소")
    public ResponseEntity deleteLikeFeed(@PathVariable Integer feedId, HttpServletRequest request) {

        int userId = (Integer) request.getAttribute("userId");

        thumbService.deleteLikeFeed(feedId, userId);

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // /feeds/5/feed-likes
    // 게시물 좋아요 유저 목록
    @GetMapping("/{feedId}/feed-likes")
    @ApiOperation(value = "피드 좋아요 유저 리스트", response = MyDto.Response.class)
    public ResponseEntity<MyDto.ResponseList> likeFeeds(@PathVariable Integer feedId, @RequestParam Integer page, HttpServletRequest request) {

        int userId = (Integer) request.getAttribute("userId");

        MyDto.ResponseList myResList = feedService.likeFeeds(feedId, page, userId);

        return new ResponseEntity<MyDto.ResponseList>(myResList, HttpStatus.OK);
    }

    // 댓글 작성
    @PostMapping("/{feedId}/comments")
    @ApiOperation(value = "댓글 작성")
    public ResponseEntity uploadComment(@PathVariable Integer feedId, @RequestBody String content, HttpServletRequest request) throws IOException, InterruptedException, FirebaseMessagingException {

        int userId = (Integer) request.getAttribute("userId");
        int commentId = commentService.uploadComment(feedId, userId, content);

        List<FcmDto> fcms = commentService.getFeedWritersFirebaseToken(feedId, userId, content, commentId);
        List<FirebaseToken> tokens = new ArrayList<>();

        for (String token : fcms.stream().map(FcmDto::getMessage).map(FcmDto.Message::getToken).collect(Collectors.toList())) tokens.add(FirebaseToken.builder().token(token).build());

        if(fcms.size() >= 1) {
            fcms.get(0).getMessage().getData().setIsRead(1);
            fcmService.sends(tokens, fcms.get(0));
        }
        fcmService.savePushAlarm(fcms.get(0));

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 댓글 수정
    @PutMapping("/{feedId}/comments/{commentId}")
    @ApiOperation(value = "댓글 수정")
    public ResponseEntity modifyComment(@PathVariable Integer commentId, @PathVariable Integer feedId, @RequestBody String content, HttpServletRequest request) {

        int myUserId = (Integer) request.getAttribute("userId");
        int userId = commentService.getUserId(commentId);

        if (myUserId != userId)
            throw new ApplicationException(HttpStatus.valueOf(403), "댓글 수정 권한 없음"); // 작성자한테만 수정 버튼 보이도록 front에서 막기

        commentService.modifyComment(commentId, content, feedId);

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 댓글 삭제
    @DeleteMapping("/{feedId}/comments/{commentId}")
    @ApiOperation(value = "댓글 삭제")
    public ResponseEntity deleteComment(@PathVariable Integer commentId, @PathVariable Integer feedId, HttpServletRequest request) {

        int userId = (Integer) request.getAttribute("userId");

        commentService.deleteComment(commentId, feedId, userId);

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // 댓글 좋아요
    @PostMapping("/{feedId}/comments/{commentId}/comment-like")
    @ApiOperation(value = "댓글 좋아요 누르기")
    public ResponseEntity likeComment(@PathVariable Integer feedId, @PathVariable Integer commentId, HttpServletRequest request) {

        int userId = (Integer) request.getAttribute("userId");

        commentService.likeComment(feedId, commentId, userId);

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 댓글 좋아요 취소
    @DeleteMapping("/{feedId}/comments/{commentId}/comment-like")
    @ApiOperation(value = "댓글 좋아요 취소")
    public ResponseEntity deleteLikeComment(@PathVariable Integer feedId, @PathVariable Integer commentId, HttpServletRequest request) {

        int userId = (Integer) request.getAttribute("userId");

        commentService.deleteLikeComment(feedId, commentId, userId);

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // /feeds/5/following/feeds
    // 내가 팔로잉하는 사람들의 피드 목록
    @GetMapping("/{userId}/following/feeds")
    @ApiOperation(value = "내가 팔로잉 하는 사람들의 피드 리스트", response = FeedDto.Response.class)
    public ResponseEntity<FeedDto.ResponseList> getFollowingFeeds(@PathVariable Integer userId,
                                                                  @RequestParam Integer page,
                                                                  HttpServletRequest request) {

        int myUserId = (Integer) request.getAttribute("userId");

        FeedDto.ResponseList feedResList = feedService.getFollowingFeeds(page * 20, myUserId);

        return new ResponseEntity<FeedDto.ResponseList>(feedResList, HttpStatus.OK);
    }


    // 피드 신고
    @PostMapping("/{feedId}/reports")
    @ApiOperation(value = "피드 신고")
    public ResponseEntity reportFeed(@PathVariable Integer feedId, @RequestBody String reason, HttpServletRequest request) {

        int userId = (Integer) request.getAttribute("userId");

        feedService.reportFeed(feedId, reason, userId);

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

}
