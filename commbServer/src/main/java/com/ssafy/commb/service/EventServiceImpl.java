package com.ssafy.commb.service;

import com.ssafy.commb.dao.EventDao;
import com.ssafy.commb.dao.FeedDao;
import com.ssafy.commb.dto.event.MyEventDto;
import com.ssafy.commb.dto.event.WeeklyEventDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private FeedDao feedDao;

    // My Event 정보 조회
    @Override
    public MyEventDto.Response getMyEvent(HttpServletRequest request) {

        MyEventDto my = eventDao.getBookmark((int) request.getAttribute("userId"));

        LocalDate now = LocalDate.now(ZoneId.of("+9"));     // Seoul Time
        int year = now.getYear();
        int month = now.getMonthValue();

        List<WeeklyEventDto> weeklyEvents = eventDao.getWeekly((int) request.getAttribute("userId"), year, month);
        List<WeeklyEventDto> weeklyEventDtoList = new ArrayList<>(4);

        for(int i=1; i<5; i++) weeklyEventDtoList.add(WeeklyEventDto.builder().week(i).weeklyParticipate(false).bookFileUrl("").build());

        for (WeeklyEventDto weeklyEvent : weeklyEvents) {
            weeklyEvent.setWeeklyParticipate(true);
            weeklyEventDtoList.set(weeklyEvent.getWeek() - 1, weeklyEvent);
        }

        my.setWeekly(weeklyEventDtoList);
        MyEventDto.Response myRes = new MyEventDto.Response();
        myRes.setData(my);

        return myRes;
    }

    // Weekly 책 추천
    @Override
    public WeeklyEventDto.Response bookRecommend(LocalDate time) {
        WeeklyEventDto weeklyEvent = eventDao.bookRecommend(time.getYear(), time.getMonthValue(), time.getDayOfMonth());

        WeeklyEventDto.Response weeklyRes = new WeeklyEventDto.Response();
        weeklyRes.setData(weeklyEvent);

        return weeklyRes;
    }

    // 주간 이벤트 도서 관련 피드 리스트 : 한명이 같은책에 대해 여러번 같은 시기에 썼을 경우 다 출력할건지 1개만 출력할 건지 정하기
    @Override
    public FeedDto.ResponseList weeklyFeeds(int weeklyId, HttpServletRequest request) {
        List<FeedDto> feeds = eventDao.weeklyFeeds(weeklyId, (int) request.getAttribute("userId"));

        for (FeedDto feed : feeds) {
            feed.setHashTags(feedDao.getHashTags(feed.getId()));
            feed.setComments(feedDao.getComments(feed.getId(), (Integer) request.getAttribute("userId")));
        }

        FeedDto.ResponseList feedRes = new FeedDto.ResponseList();

        feedRes.setData(feeds);
        return feedRes;
    }

    @Override
    public Integer getWeeklyParticipantsCnt(int weeklyId) {
        Integer participantsCnt = eventDao.getWeeklyParticipantsCnt(weeklyId);
        return participantsCnt;
    }

    @Override
    public MyDto.ResponseList getWeeklyParticipants(int weeklyId, HttpServletRequest request) {
        List<MyDto> mys = eventDao.getWeeklyParticipants(weeklyId, (int) request.getAttribute("userId"));

        MyDto.ResponseList myResList = new MyDto.ResponseList();
        myResList.setData(mys);

        return myResList;
    }


}
