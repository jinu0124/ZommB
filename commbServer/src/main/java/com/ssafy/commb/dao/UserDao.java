package com.ssafy.commb.dao;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    public UserDto userInfo(int userId, int myUserId);

    public List<UserDto> getMyFollowerExFollowing(int userId);

    public List<MyDto> getUsers(String nickname, String userId);

}
