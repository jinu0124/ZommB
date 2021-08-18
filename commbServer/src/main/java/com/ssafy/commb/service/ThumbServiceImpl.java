package com.ssafy.commb.service;

import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.Feed;
import com.ssafy.commb.model.Thumb;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.FeedRepository;
import com.ssafy.commb.repository.ThumbRepository;
import com.ssafy.commb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ThumbServiceImpl implements ThumbService {

    @Autowired
    private ThumbRepository thumbRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private UserRepository userRepository;

    public void likeFeed(int feedId, int userId) {

        Optional<Feed> feed = feedRepository.findById(feedId);
        Optional<User> user = userRepository.findById(userId);

        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "존재하지 않는 피드입니다!");

        if (isExist(feed.get(), user.get())) throw new ApplicationException(HttpStatus.valueOf(400), "이미 좋아요를 누르셨습니다!");

        // 좋아요 추가
        Thumb thumb = new Thumb();
        thumb.setFeed(feed.get());
        thumb.setUser(user.get());

        thumbRepository.save(thumb);
    }

    @Transactional
    public void deleteLikeFeed(int feedId, int userId){

        Optional<Feed> feed = feedRepository.findById(feedId);
        Optional<User> user = userRepository.findById(userId);

        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "존재하지 않는 피드입니다!");

        Optional<Thumb> thumb = thumbRepository.findByFeedAndUser(feed.get(), user.get());

        if(!thumb.isPresent()) throw new ApplicationException(HttpStatus.valueOf(404), "좋아요를 누른 피드가 아닙니다!");

        thumbRepository.delete(thumb.get());
    }

    public Boolean isExist(Feed feed, User user) {
        Optional<Thumb> thumb = thumbRepository.findByFeedAndUser(feed, user);

        if (thumb.isPresent()) return true;

        return false;
    }

    public int getUserId(int feedId, int userId){
        Optional<Feed> feed = feedRepository.findById(feedId);
        Optional<User> user = userRepository.findById(userId);

        Optional<Thumb> thumb = thumbRepository.findByFeedAndUser(feed.get(), user.get());
        if(!thumb.isPresent()) throw new ApplicationException(HttpStatus.valueOf(404), "좋아요를 누른 피드가 없습니다!");

        return thumb.get().getUser().getId();
    }
}
