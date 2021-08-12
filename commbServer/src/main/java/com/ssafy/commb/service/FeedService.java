package com.ssafy.commb.service;

import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public interface FeedService {

    public void uploadFeed(FeedDto.RegisterRequest feedReq, MultipartHttpServletRequest request) throws IOException, ServletException;

    public FeedDto.ResponseList getUserFeed(int userId, int page, HttpServletRequest request);

    public int getUserFeedCnt(int userId);

    public void modifyFeed(String content, int feedId);

    public int getUserId(int feedId);

    public void deleteFeed(int feedId);

    public void reportFeed(int feedId, String reason, int userId);

    public FeedDto.ResponseList getFollowingFeeds(int page, int userId);

    public MyDto.ResponseList likeFeeds(int feedId, int userId);

    public FeedDto.ResponseList getFeeds(String searchWord, int page, int userId);

}
