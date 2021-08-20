package com.ssafy.commb.service;

public interface ThumbService {
    public void likeFeed(int feedId, int userId);

    public void deleteLikeFeed(int feedId, int userId);

    public int getUserId(int feedId, int userId);
}
