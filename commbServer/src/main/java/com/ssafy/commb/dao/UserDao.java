package com.ssafy.commb.dao;

import com.ssafy.commb.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public UserDto userInfo(int userId, int myUserId);
}
