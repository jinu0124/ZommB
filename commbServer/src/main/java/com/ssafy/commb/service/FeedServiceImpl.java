package com.ssafy.commb.service;

import com.ssafy.commb.dao.FeedDao;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.feed.HashTagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService{

    @Autowired
    private FeedDao feedDao;

    @Override
    public FeedDto.ResponseList getUserFeed(int userId, HttpServletRequest request) {

        List<FeedDto> feeds = feedDao.userFeed(userId, (Integer) request.getAttribute("userId"));

        for(FeedDto feed : feeds){
            feed.setHashTags(feedDao.getHashTags(feed.getId()));
            feed.setComments(feedDao.getComments(feed.getId()));
        }

        FeedDto.ResponseList feedResList = new FeedDto.ResponseList();
        feedResList.setData(feeds);

        return feedResList;
    }

    @Override
    public int getUserFeedCnt(int userId) {
        return feedDao.userFeedCnt(userId);
    }
}
