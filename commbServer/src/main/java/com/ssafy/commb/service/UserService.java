package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;

public interface UserService {
    public void joinUser(MyDto.Request myReq);
    public boolean isExistEmail(String email);
    public MyDto login(MyDto.LoginRequest myReq);
    public String TokenGeneration(int userId, String receiverEmail);
    public void confirmEmail(String token);

}
