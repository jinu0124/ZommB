package com.ssafy.commb.service;

public interface FollowService {

    public void addFollowing(int follower, int following);

    public void deleteFollowing(int follower, int following);
}
