package com.ssafy.commb.service;

import com.ssafy.commb.dao.EventDao;
import com.ssafy.commb.dao.FeedDao;
import com.ssafy.commb.dto.event.DailyEventDto;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private FeedDao feedDao;

    /**
     * My Event 정보 조회
     * @param request : 유저 ID
     * @return : 내 이벤트 관련 정보
     */
    @Override
    public MyEventDto.Response getMyEvent(HttpServletRequest request) {

        MyEventDto my = eventDao.getBookmark((int) request.getAttribute("userId"));

        LocalDate now = LocalDate.now(ZoneId.of("+9"));                                                                     // Seoul Time
        int year = now.getYear();
        int month = now.getMonthValue();

        List<WeeklyEventDto> weeklyEvents = eventDao.getWeekly((int) request.getAttribute("userId"), year, month);

        my.setWeekly(weeklyEvents);
        MyEventDto.Response myRes = new MyEventDto.Response();
        myRes.setData(my);

        return myRes;
    }

    /**
     * Weekly 도서 추천
     * @param time : 시간 정보 String(yyyy-MM-dd)
     * @return : 주간 추천 도서 반환
     */
    @Override
    public WeeklyEventDto.Response bookRecommend(String time) {
        LocalDate date = LocalDate.parse(time, DateTimeFormatter.ISO_DATE);
        WeeklyEventDto weeklyEvent = eventDao.bookRecommend(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        WeeklyEventDto.Response weeklyRes = new WeeklyEventDto.Response();
        weeklyRes.setData(weeklyEvent);

        return weeklyRes;
    }

    /**
     * 주간 이벤트 도서 관련 피드 리스트 ? 1인이 같은 책에 대한 피드를 동 이벤트 시기에 여러번 썼을 경우 피드를 최신 1개만 줄지 or 다줄지
     * @param weeklyId : 주간 이벤트 ID
     * @param request : 유저 ID
     * @return : 피드 리스트
     */
    @Override
    public FeedDto.ResponseList weeklyFeeds(int weeklyId, int page, HttpServletRequest request) {
        List<FeedDto> feeds = eventDao.weeklyFeeds(weeklyId, page, (int) request.getAttribute("userId"));

        if(feeds.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        return FeedDto.ResponseList.builder()
                .data(getHashAndComments(feeds, (int) request.getAttribute("userId")))
                .build();
    }

    /**
     * 주간 이벤트 참여자 수
     * @param weeklyId : 주간 이벤트 ID
     * @return : 참여자 수
     */
    @Override
    public Integer getWeeklyParticipantsCnt(int weeklyId) {

        return eventDao.getWeeklyParticipantsCnt(weeklyId);
    }

    /**
     * 주간 이벤트 참여자 목록
     * @param weeklyId : 주간 이벤트 ID
     * @param request : 유저 ID
     * @return : 참여자 목록
     */
    @Override
    public MyDto.ResponseList getWeeklyParticipants(int weeklyId, int page, HttpServletRequest request) {
        List<MyDto> mys = eventDao.getWeeklyParticipants(weeklyId, page, (int) request.getAttribute("userId"));

        if(mys.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        return MyDto.ResponseList.builder().data(mys).build();
    }

    /**
     * 일일 키워드 추천
     * @param today : 오늘 날짜 (yyyy-MM-dd)
     * @return : 일일 키워드 정보 반환
     */
    @Override
    public DailyEventDto.Response keywordRecommend(String today) {

        DailyEventDto daily = eventDao.keywordRecommend(today);

        DailyEventDto.Response dailyRes = new DailyEventDto.Response();
        dailyRes.setData(daily);

        return dailyRes;
    }

    /**
     * 일일 이벤트 참여 피드들 가져오기
     * @param dailyId : 일일 이벤트 ID
     * @param request : 유저 ID
     * @return : 피드 리스트
     */
    @Override
    public FeedDto.ResponseList dailyFeeds(int dailyId, int page, HttpServletRequest request) {
        List<FeedDto> feeds = eventDao.dailyFeeds(dailyId, page, (int) request.getAttribute("userId"));

        if(feeds.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        return FeedDto.ResponseList.builder()
                .data(getHashAndComments(feeds, (int) request.getAttribute("userId")))
                .build();
    }

    /**
     * 일일 이벤트 참여자 리스트
     * @param dailyId : Daily Event ID
     * @param userId : 유저 ID
     * @return : 참여자
     */
    @Override
    public MyDto.ResponseList getDailyParticipants(int dailyId, int page, int userId) {
        List<MyDto> mys = eventDao.getDailyParticipants(dailyId, page, userId);

        if(mys.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        return MyDto.ResponseList.builder().data(mys).build();
    }

    /**
     * 일일 참여자 수
     * @param dailyId : 일일 이벤트 ID
     * @return : 참여자 수
     */
    @Override
    public Integer getDailyParticipantsCnt(int dailyId) {
        Integer participantsCnt = eventDao.getDailyParticipantsCnt(dailyId);
        return participantsCnt;
    }

    /**
     * 피드에 대한 해시태그와 댓글 정보 리스트 가져오기
     * @param feeds : Feed 들
     * @param userId : 유저 ID
     * @return : 해시태그, 댓글 정보 리스트 반환
     */
    private List<FeedDto> getHashAndComments(List<FeedDto> feeds, int userId){
        for (FeedDto feed : feeds) {
            feed.setHashTags(feedDao.getHashTags(feed.getId()));
            feed.setComments(feedDao.getComments(feed.getId(), userId));
        }
        return feeds;
    }

}
