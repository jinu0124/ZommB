package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public int joinUser(MyDto.Request myReq);
    public boolean isExistEmail(String email);
    public MyDto login(MyDto.LoginRequest myReq);
    public String TokenGeneration(int userId, String receiverEmail);
    public void confirmEmail(String token);
    public boolean updatePassword(UserDto.ModifyPwRequest userReq, HttpServletRequest request);
    public boolean validatePassword(String password);

    public void deleteUser(int userId);
}
