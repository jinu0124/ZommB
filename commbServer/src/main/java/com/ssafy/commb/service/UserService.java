package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public UserDto.ResponseList getUsers(String nickname);

    public int joinUser(MyDto.Request myReq);

    public boolean isExistEmail(String email);

    public MyDto.Response login(MyDto.LoginRequest myReq);

    public String TokenGeneration(int userId, String receiverEmail);

    public boolean confirmEmail(String token);

    public void updatePassword(UserDto.ModifyPwRequest userReq, HttpServletRequest request);

    public void validatePassword(String password);

    public void deleteUser(int userId);

    public UserDto.Response getUserInfo(int userId, HttpServletRequest request);

}
