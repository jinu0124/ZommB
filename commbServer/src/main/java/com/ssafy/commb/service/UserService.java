package com.ssafy.commb.service;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {
    public UserDto.ResponseList getUsers(String nickname, int page);

    public MyDto.ResponseList getUsers(String nickname, int page, HttpServletRequest request);

    public int joinUser(MyDto.Request myReq);

    public boolean isExistEmail(String email);

    public MyDto.Response login(MyDto.LoginRequest myReq);

    public String TokenGeneration(int userId, String receiverEmail, String url);

    public boolean confirmEmail(String token);

    public boolean confirmEmailForPassword(String token);

    public void updatePassword(UserDto.ModifyPwRequest userReq, HttpServletRequest request);
    public void updatePassword(int userId, String password, int tmp);

    public void validatePassword(String password);

    public void deleteUser(int userId);

    public UserDto.Response getUserInfo(int userId, HttpServletRequest request);

    public UserDto.ResponseList followRecommend(int page, HttpServletRequest request);

    public int getUserInfoByEmail(String email);

    public MyDto.Response socialLogin(int userId);

    public String getUserRole(int userId);




}
