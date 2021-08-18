package com.ssafy.commb.service;

import com.ssafy.commb.dao.FeedDao;
import com.ssafy.commb.dto.fcm.FcmDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.Comment;
import com.ssafy.commb.model.CommentThumb;
import com.ssafy.commb.model.Feed;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.CommentRepository;
import com.ssafy.commb.repository.CommentThumbRepository;
import com.ssafy.commb.repository.FeedRepository;
import com.ssafy.commb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentThumbRepository commentThumbRepository;

    @Autowired
    private FeedDao feedDao;

    public int uploadComment(int feedId, int userId, String content) {
        Comment comment = new Comment();

        Optional<Feed> feed = feedRepository.findById(feedId);
        Optional<User> user = userRepository.findById(userId);

        comment.setFeed(feed.get());
        comment.setUser(user.get());
        comment.setContent(content);

        return commentRepository.save(comment).getId();
    }

    public void modifyComment(int commentId, String content, int feedId) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (!comment.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "댓글이 존재하지 않습니다");

        if (comment.get().getFeed().getId() != feedId)
            throw new ApplicationException(HttpStatus.valueOf(400), "이 피드에 댓글을 작성하지 않았습니다");

        comment.ifPresent(comments -> {
            comments.setContent(content);
            comments.setIsMod(1); // 댓글 수정
            commentRepository.save(comments);
        });

    }

    public int getUserId(int commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.get().getUser().getId();
    }

    public void deleteComment(int commentId, int feedId, int userId) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (!comment.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "댓글이 존재하지 않습니다");

        if (comment.get().getUser().getId() == userId || comment.get().getFeed().getUser().getId() == userId)
            commentRepository.deleteById(commentId);
        else
            throw new ApplicationException(HttpStatus.valueOf(403), "댓글 삭제 권한 없음");
    }

    public void likeComment(int feedId, int commentId, int userId){

        Optional<Feed> feed = feedRepository.findById(feedId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<User> user = userRepository.findById(userId);

        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "존재하지 않는 피드입니다!");

        if (!comment.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "존재하지 않는 댓글입니다!");

        if (comment.get().getFeed().getId() != feedId) throw new ApplicationException(HttpStatus.valueOf(400), "해당 피드에 존재하지 않는 댓글입니다!");

        if(isExist(comment.get(), user.get())) throw new ApplicationException(HttpStatus.valueOf(400), "이미 좋아요를 누르셨습니다!");

        // 좋아요 추가
        CommentThumb commentThumb = new CommentThumb();
        commentThumb.setComment(comment.get());
        commentThumb.setUser(user.get());

        commentThumbRepository.save(commentThumb);
    }

    public void deleteLikeComment(int feedId, int commentId, int userId){

        Optional<Feed> feed = feedRepository.findById(feedId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<User> user = userRepository.findById(userId);

        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "존재하지 않는 피드입니다!");

        if (!comment.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "존재하지 않는 댓글입니다!");

        if (comment.get().getFeed().getId() != feedId) throw new ApplicationException(HttpStatus.valueOf(400), "해당 피드에 존재하지 않는 댓글입니다!");

        Optional<CommentThumb> commentThumb = commentThumbRepository.findByCommentAndUser(comment.get(), user.get());

        if(!commentThumb.isPresent()) throw new ApplicationException(HttpStatus.valueOf(404), "좋아요를 누른 댓글이 아닙니다!");

        commentThumbRepository.delete(commentThumb.get());
    }

    @Override
    public List<FcmDto> getFeedWritersFirebaseToken(int feedId, int userId, String content, int commentId) {
        List<FcmDto> fcms = new ArrayList<>();

        List<String> tokens = feedDao.getFeedWriterToken(feedId);
        Optional<User> user = userRepository.findById(userId);
        Optional<Feed> feed = feedRepository.findById(feedId);

        for(String token : tokens) {
            FcmDto fcm = FcmDto.builder()
                    .message(FcmDto.Message.builder().token(token)
                            .notification(FcmDto.Notification.builder()
                                    .title("comment")
                                    .body(content)
                                    .build())
                            .data(FcmDto.PayData.builder()
                                    .feedId(feedId)
                                    .userId(userId)
                                    .nickname(user.get().getNickname())
                                    .userFileUrl(user.get().getFileUrl())
                                    .comment(content)
                                    .commentId(commentId)
                                    .createAt(LocalDateTime.now(ZoneId.of("+9")))
                                    .targetUserId(feed.get().getUser().getId())
                                    .isRead(0)
                                    .build())
                            .build())
                    .build();

            fcms.add(fcm);
        }

        return fcms;
    }

    public Boolean isExist(Comment comment, User user){
        Optional<CommentThumb> commentThumb = commentThumbRepository.findByCommentAndUser(comment, user);

        if(commentThumb.isPresent()) return true;

        return false;
    }

}
