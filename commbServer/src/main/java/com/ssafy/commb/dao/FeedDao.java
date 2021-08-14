package com.ssafy.commb.dao;

import com.ssafy.commb.dto.feed.CommentDto;
import com.ssafy.commb.dto.feed.FeedDto;
import com.ssafy.commb.dto.feed.HashTagDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FeedDao {
    public List<FeedDto> userFeed(int userId, int page, int myUserId);

    public List<HashTagDto> getHashTags(int feedId);
    public List<CommentDto> getComments(int feedId, int userId);

    public int userFeedCnt(int userId);

    public List<FeedDto> getFollowingFeeds(int page, int userId);

    public List<FeedDto> getFeeds(Map<String, Object> map);

    public List<String> getFeedWriterToken(int feedId);
}
