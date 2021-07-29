package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.model.ConfirmationToken;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void joinUser(MyDto.Request myReq) {
        User user = new User();
        user.setEmail(myReq.getEmail());
        user.setPassword(myReq.getPassword());
        user.setName(myReq.getName());
        user.setNickname(myReq.getNickname());

        userRepository.save(user);
    }

    public boolean isExistEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public MyDto login(MyDto.LoginRequest myReq) {
        User user = userRepository.findByEmailAndPassword(myReq.getEmail(), myReq.getPassword());

        if (user == null) return null;

        MyDto my = new MyDto();
        my.setId(user.getId());
        my.setNickname(user.getNickname());
        my.setUserFileUrl(user.getFileUrl());

        return my;
    }

    private final ConfirmationTokenService confirmationTokenService;

    // 토큰 생성
    public String TokenGeneration(int userId, String receiverEmail){
        String token = confirmationTokenService.createEmailConfirmationToken(userId, receiverEmail);
        return token;
    }

    // 이메일 인증 로직
    public void confirmEmail(String token) {
        ConfirmationToken findConfirmationToken = confirmationTokenService.findByIdAndExpirationDateAfterAndExpired(token);
        User user = userRepository.findById(findConfirmationToken.getUserId());
        findConfirmationToken.useToken();   // 토큰 만료 로직
        user.setRole("USR");    // 유저의 이메일 인증 값 변경 로직
    }

}
