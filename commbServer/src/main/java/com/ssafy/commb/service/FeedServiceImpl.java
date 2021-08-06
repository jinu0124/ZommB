package com.ssafy.commb.service;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.*;
import com.ssafy.commb.repository.FeedRepository;
import com.ssafy.commb.repository.ReportRepository;
import com.ssafy.commb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ssafy.commb.dao.FeedDao;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private FeedDao feedDao;

    @Autowired
    private S3Service S3service;

    public void uploadFeed(FeedDto.RegisterRequest feedReq, MultipartHttpServletRequest request) throws IOException, ServletException {

        User user = new User();
        Book book = new Book();
        Feed feed = new Feed();

        user.setId((Integer) request.getAttribute("userId"));
        book.setId(feedReq.getBookId());
        feed.setUser(user);
        feed.setBook(book);
        feed.setContent(feedReq.getContent());

        Part part = S3service.extractFile(request.getParts()); // 파일 하나 받아옴
        String fileUrl =  S3service.uploadS3(part, "feed");
        feed.setFileUrl(fileUrl);

        feedRepository.save(feed);
    }

    @Override
    public FeedDto.ResponseList getUserFeed(int userId, HttpServletRequest request) {

        List<FeedDto> feeds = feedDao.userFeed(userId, (Integer) request.getAttribute("userId"));

        for (FeedDto feed : feeds) {
            feed.setHashTags(feedDao.getHashTags(feed.getId()));
            feed.setComments(feedDao.getComments(feed.getId(), (Integer) request.getAttribute("userId")));
        }

        FeedDto.ResponseList feedResList = new FeedDto.ResponseList();
        feedResList.setData(feeds);

        return feedResList;
    }

    @Override
    public int getUserFeedCnt(int userId) {
        return feedDao.userFeedCnt(userId);
    }

    public void modifyFeed(String content, int feedId) {
        Optional<Feed> feed = feedRepository.findById(feedId);
        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "변경할 피드가 없습니다.");

        feed.ifPresent(feedSelect -> {
            feedSelect.setContent(content);
            feedRepository.save(feedSelect);
        });
    }

    public int getUserId(int feedId) {
        Optional<Feed> feed = feedRepository.findById(feedId);
        return feed.get().getUser().getId();
    }

    public void deleteFeed(int feedId) {
        Optional<Feed> feed = feedRepository.findById(feedId);
        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "삭제할 피드가 없습니다.");

        String url = feed.get().getFileUrl();
        S3service.deleteS3(url, "feed");

        feedRepository.deleteById(feedId);
    }

    public void reportFeed(int feedId, String reason, int userId){
        Report report = new Report();

        Optional<Feed> feed = feedRepository.findById(feedId);
        Optional<User> user = userRepository.findById(userId);

        if (!feed.isPresent()) throw new ApplicationException(HttpStatus.valueOf(400), "신고할 피드가 없습니다.");

        report.setFeed(feed.get());
        report.setUser(user.get());
        report.setReason(reason);

        reportRepository.save(report);
    }

    public FeedDto.ResponseList getFollowingFeeds(int userId){

        List<FeedDto> feeds = feedDao.getFollowingFeeds(userId);
        System.out.println("fewfwegwewge");
        for (FeedDto feed : feeds) {
            feed.setHashTags(feedDao.getHashTags(feed.getId()));
            feed.setComments(feedDao.getComments(feed.getId(), userId));
        }
        System.out.println(feeds.get(0).getComments());
        FeedDto.ResponseList feedResList = new FeedDto.ResponseList();
        feedResList.setData(feeds);

        return feedResList;
    }


}
