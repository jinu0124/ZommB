package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.model.ConfirmationToken;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

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
        Optional<User> user = userRepository.findByEmailAndPassword(myReq.getEmail(), myReq.getPassword());

        if (!user.isPresent()) return null;

        MyDto my = new MyDto();
        my.setId(user.get().getId());
        my.setNickname(user.get().getNickname());
        my.setUserFileUrl(user.get().getFileUrl());

        return my;
    }

    private final ConfirmationTokenServiceImpl confirmationTokenService;

    // 토큰 생성
    public String TokenGeneration(int userId, String receiverEmail){
        return confirmationTokenService.createEmailConfirmationToken(userId, receiverEmail);
    }

    // 이메일 인증 로직
    public void confirmEmail(String token) {
        ConfirmationToken findConfirmationToken = confirmationTokenService.findByIdAndExpirationDateAfterAndExpired(token);
        Optional<User> user = userRepository.findById(findConfirmationToken.getUserId());
        findConfirmationToken.useToken();   // 토큰 만료 로직
        user.get().setRole("USR");    // 유저의 이메일 인증 값 변경 로직
    }

    @Override
    public boolean updatePassword(UserDto.ModifyPwRequest userReq, HttpServletRequest request) {
//        int userId = 10000001;
//        Optional<User> user = userRepository.findByIdAndPassword(userId, userReq.getOldPassword()); // 테스트용

        Optional<User> user = userRepository.findByIdAndPassword((int) request.getAttribute("userId"), userReq.getOldPassword());
        System.out.println(userReq.getNewPassword());

        if(!user.isPresent()) {
            System.out.println(user.get().getEmail());
            return false;
        }

        user.ifPresent(userSelect -> {
            userSelect.setPassword(userReq.getNewPassword());
            userRepository.save(userSelect);
        });

        return true;
    }

    public boolean checkPassword( String password){
         String pattern = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}";
         return password.matches(pattern);
    }

}
