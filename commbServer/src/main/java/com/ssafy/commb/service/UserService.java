package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

}
