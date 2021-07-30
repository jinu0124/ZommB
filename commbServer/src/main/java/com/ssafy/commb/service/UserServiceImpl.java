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
import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    /* for production after build code */
//    static String uploadPath = request.getSession().getServletContext().getRealPath("/");

    /* for eclipse development before build code */
    static String uploadPath = "C:" + File.separator + "Users" + File.separator + "jinwoo"
            + File.separator + "IdeaProjects"
            + File.separator + "SUBPJT1"
            + File.separator + "commbServer"
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "static";

    @Autowired
    UserRepository userRepository;

    public int joinUser(MyDto.Request myReq) {
        User user = new User(myReq.getEmail(), myReq.getPassword(), myReq.getName(), myReq.getNickname());

        return userRepository.save(user).getId();
    }

    public boolean isExistEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public MyDto login(MyDto.LoginRequest myReq) {
        Optional<User> user = userRepository.findByEmailAndPassword(myReq.getEmail(), myReq.getPassword());

        if (!user.isPresent()) return null;
        MyDto my = new MyDto();
        my.setId(user.get().getId());
        my.setNickname(user.get().getNickname());
        my.setUserFileUrl(user.get().getFileUrl() != null ? user.get().getFileUrl() : "");

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
        Optional<User> user = userRepository.findByIdAndPassword((int) request.getAttribute("userId"), userReq.getOldPassword());
        System.out.println(userReq.getNewPassword());

        if(!user.isPresent()) return false;

        user.ifPresent(userSelect -> {
            userSelect.setPassword(userReq.getNewPassword());
            userRepository.save(userSelect);
        });

        return true;
    }

    public boolean validatePassword( String password){
         String pattern = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}";
         return password.matches(pattern);
    }

    @Override
    public void deleteUser(int userId) {
        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()) return;

        userRepository.deleteById(userId);

        // 기존 물리 파일 삭제 : DB에서 기존 파일의 물리 경로 가져와서 물리 파일 삭제하기
        File file = new File(uploadPath + File.separator + user.get().getFileUrl());
        if(file.exists()) file.delete();
    }

}
