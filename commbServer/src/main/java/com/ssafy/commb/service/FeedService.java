package com.ssafy.commb.service;

import com.ssafy.commb.dto.feed.FeedDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public interface FeedService {
    public FeedDto uploadFeed(FeedDto.RegisterRequest feedReq, MultipartHttpServletRequest request) throws IOException, ServletException;

    public FeedDto.ResponseList getUserFeed(int userId, HttpServletRequest request);

    public int getUserFeedCnt(int userId);

    public void modifyFeed(String content, int feedId);

    public int getUserId(int feedId);

    public void deleteFeed(int feedId);
}
