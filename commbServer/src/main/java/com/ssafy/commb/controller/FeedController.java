package com.ssafy.commb.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.commb.common.fcm.FcmService;
import com.ssafy.commb.dto.fcm.FcmDto;
import com.ssafy.commb.dto.feed.CommentDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.feed.ReasonDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.Comment;
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
    public ResponseEntity modifyFeed(@RequestBody FeedDto.RequestContent content, @PathVariable Integer feedId, HttpServletRequest request) {

        System.out.println(content.getContent());
        if (content == null) return ResponseEntity.status(400).build();

        int myUserId = (Integer) request.getAttribute("userId");
        int userId = feedService.getUserId(feedId);

        if (myUserId != userId)
            throw new ApplicationException(HttpStatus.valueOf(403), "게시물 수정 권한 없음"); // 작성자한테만 수정 버튼 보이도록 front에서 막기

        feedService.modifyFeed(content.getContent(), feedId);

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
                                .userFileUrl(user.getData().getUserFileUrl())
                                .feedId(feed.getId())
                                .feedFileUrl(feed.getFeedFileUrl())
                                .content(feed.getContent())
                                .targetUserId(feed.getUser().getId())
                                .createAt(LocalDateTime.now(ZoneId.of("+9")))
                                .isRead(firebaseToken.size() == 0 ? 0 : 1)
                                .build())
                        .notification(FcmDto.Notification.builder()
                                .title("like")
                                .body(user.getData().getNickname())
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

        int myUserId = (Integer) request.getAttribute("userId");
        int userId = thumbService.getUserId(feedId, myUserId);

        if (myUserId != userId)
            throw new ApplicationException(HttpStatus.valueOf(403), "피드 좋아요 취소 권한 없음");

        thumbService.deleteLikeFeed(feedId, myUserId);

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
    public ResponseEntity uploadComment(@PathVariable Integer feedId, @RequestBody CommentDto.RequestComment comment, HttpServletRequest request) throws IOException, InterruptedException, FirebaseMessagingException {

        int userId = (Integer) request.getAttribute("userId");
        int commentId = commentService.uploadComment(feedId, userId, comment.getComment());

        List<FcmDto> fcms = commentService.getFeedWritersFirebaseToken(feedId, userId, comment.getComment(), commentId);
        List<FirebaseToken> tokens = new ArrayList<>();

        for (String token : fcms.stream().map(FcmDto::getMessage).map(FcmDto.Message::getToken).collect(Collectors.toList())) tokens.add(FirebaseToken.builder().token(token).build());

        if(fcms.size() >= 1) {
            fcms.get(0).getMessage().getData().setIsRead(1);
            fcmService.sends(tokens, fcms.get(0));
            fcmService.savePushAlarm(fcms.get(0));
        }
        else{
            fcmService.savePushAlarm(FcmDto.builder()
                    .message(FcmDto.Message
                            .builder()
                            .notification(FcmDto.Notification.builder()
                                .title("comment")
                                .body(comment.getComment())
                                .build())
                            .data(FcmDto.PayData.builder()
                                .userId(userId)
                                .feedId(feedId)
                                .commentId(commentId)
                                .targetUserId(feedService.getUserId(feedId))
                                .isRead(0)
                                .createAt(LocalDateTime.now(ZoneId.of("+9")))
                                .build())
                            .build())
                    .build());
        }

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    // 댓글 수정
    @PutMapping("/{feedId}/comments/{commentId}")
    @ApiOperation(value = "댓글 수정")
    public ResponseEntity modifyComment(@PathVariable Integer commentId, @PathVariable Integer feedId, @RequestBody CommentDto.RequestComment comment, HttpServletRequest request) {

        int myUserId = (Integer) request.getAttribute("userId");
        int userId = commentService.getUserId(commentId);

        if (myUserId != userId)
            throw new ApplicationException(HttpStatus.valueOf(403), "댓글 수정 권한 없음"); // 작성자한테만 수정 버튼 보이도록 front에서 막기

        commentService.modifyComment(commentId, comment.getComment(), feedId);

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

        int myUserId = (Integer) request.getAttribute("userId");
        int userId = feedService.getUserId(feedId);

        if (myUserId != userId)
            throw new ApplicationException(HttpStatus.valueOf(403), "댓글 좋아요 취소 권한 없음");

        commentService.deleteLikeComment(feedId, commentId, myUserId);

        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    // /feeds/5/following/feeds
    // 내가 팔로잉하는 사람들의 피드 목록(내 피드 포함)
    @GetMapping("/{userId}/following/feeds")
    @ApiOperation(value = "내가 팔로잉 하는 사람들의 피드 리스트(내 피드 포함)", response = FeedDto.Response.class)
    public ResponseEntity<FeedDto.ResponseList> getFollowingFeeds(@PathVariable Integer userId,
                                                                  @RequestParam Integer page,
                                                                  HttpServletRequest request) {

        int myUserId = (int) request.getAttribute("userId");

        FeedDto.ResponseList feedResList = feedService.getFollowingFeeds(page * 20, myUserId);

        return new ResponseEntity<FeedDto.ResponseList>(feedResList, HttpStatus.OK);
    }


    // 피드 신고
    @PostMapping("/{feedId}/reports")
    @ApiOperation(value = "피드 신고")
    public ResponseEntity reportFeed(@PathVariable Integer feedId, @RequestBody ReasonDto reason, HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        System.out.println(reason.getReason());

        feedService.reportFeed(feedId, reason.getReason(), userId);

        return new ResponseEntity(HttpStatus.valueOf(201));
    }

    @GetMapping("/{feedId}")
    @ApiOperation(value = "해당 피드 상세 조회")
    public ResponseEntity<FeedDto.Response> searchFeed(@PathVariable Integer feedId, HttpServletRequest request){
        int userId = (Integer) request.getAttribute("userId");

        FeedDto.Response response = feedService.searchFeed(feedId, userId);

        return new ResponseEntity<FeedDto.Response>(response, HttpStatus.OK);
    }

}
