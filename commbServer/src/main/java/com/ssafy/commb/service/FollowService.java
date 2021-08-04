package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;

import java.util.List;

public interface FollowService {

    public void addFollowing(int follower, int following);

    public void deleteFollowing(int follower, int following);

    public List<MyDto> getFollowings(int meId, int userId);

    public List<MyDto> getFollowers(int meId, int userId);
}
