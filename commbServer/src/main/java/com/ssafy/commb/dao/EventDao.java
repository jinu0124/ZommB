package com.ssafy.commb.dao;

import com.ssafy.commb.dto.event.DailyEventDto;
import com.ssafy.commb.dto.event.MyEventDto;
import com.ssafy.commb.dto.event.WeeklyEventDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventDao {
    public MyEventDto getBookmark(int userId);

    public List<WeeklyEventDto> getWeekly(int userId, int year, int month);


    public WeeklyEventDto bookRecommend(int year, int month, int day);

    public List<FeedDto> weeklyFeeds(int weeklyId, int userId);

    public Integer getWeeklyParticipantsCnt(int weeklyId);

    public List<MyDto> getWeeklyParticipants(int weeklyId, int userId);


    public DailyEventDto keywordRecommend(String param);

    public List<FeedDto> dailyFeeds(int dailyId, int userId);

    public List<MyDto> getDailyParticipants(int dailyId, int userId);

    public Integer getDailyParticipantsCnt(int dailyId);


}
