package com.ssafy.commb.service;

import com.ssafy.commb.dto.feed.FeedDto;

import javax.servlet.http.HttpServletRequest;

public interface FeedService {
    public FeedDto.ResponseList getUserFeed(int userId, HttpServletRequest request);
    public int getUserFeedCnt(int userId);
}
