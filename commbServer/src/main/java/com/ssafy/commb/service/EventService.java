package com.ssafy.commb.service;

import com.amazonaws.services.xray.model.Http;
import com.ssafy.commb.dto.event.DailyEventDto;
import com.ssafy.commb.dto.event.MyEventDto;
import com.ssafy.commb.dto.event.WeeklyEventDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public interface EventService {
    public MyEventDto.Response getMyEvent(HttpServletRequest request);

    public WeeklyEventDto.Response bookRecommend(String time);

    public FeedDto.ResponseList weeklyFeeds(int weeklyId, HttpServletRequest request);

    public Integer getWeeklyParticipantsCnt(int weeklyId);

    public MyDto.ResponseList getWeeklyParticipants(int weeklyId, HttpServletRequest request);

    public DailyEventDto.Response keywordRecommend(String today);

    public FeedDto.ResponseList dailyFeeds(int keywordId, HttpServletRequest request);

    public MyDto.ResponseList getDailyParticipants(int dailyId, int userId);

    public Integer getDailyParticipantsCnt(int dailyId);



}
