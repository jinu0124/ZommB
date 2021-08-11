package com.ssafy.commb.dao;

import com.ssafy.commb.dto.feed.CommentDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.feed.HashTagDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedDao {
    public List<FeedDto> userFeed(int userId, int myUserId);

    public List<HashTagDto> getHashTags(int feedId);
    public List<CommentDto> getComments(int feedId, int userId);

    public int userFeedCnt(int userId);

    public List<FeedDto> getFollowingFeeds(int userId);

    public List<FeedDto> getFeeds(String dynamicQuery, String userId, String wordCnt);


}
