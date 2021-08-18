package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;


@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    /**
     * 프로필 변경 기능
     * @param myReq : 변경 닉네임, 프로필 이미지 변경 flag
     * @param request : 유저 ID
     * @return : 변경 프로필 이미지 경로 및 변경 정보 반환
     * @throws IOException : IO (AWS)
     * @throws ServletException : Servlet
     */
    @Transactional
    @Override
    public MyDto.Response updateProfile(MyDto.ModifyRequest myReq, MultipartHttpServletRequest request) throws IOException, ServletException {
        int userId = Integer.parseInt(String.valueOf(request.getAttribute("userId")));
        MyDto.Response myRes = new MyDto.Response();
        Collection<Part> parts = request.getParts();

        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()) throw new ApplicationException(HttpStatus.GONE, "회원정보 조회 실패");
        MyDto my = MyDto.builder().userFileUrl(user.get().getFileUrl()).nickname(myReq.getNickname()).id(user.get().getId()).build();

        if(myReq.getFlag() > 2) throw new ApplicationException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        else if(myReq.getFlag() != 0){
            // S3 파일 삭제
            if(my.getUserFileUrl() != null)  s3Service.deleteS3(my.getUserFileUrl(), "profile");
        }

        if(myReq.getFlag() == 0) {                                          // 프로필 이미지 유지
            updateDb(user, user.get().getFileUrl(), myReq.getNickname());
        }
        else if(myReq.getFlag() == 1) {                                      // 프로필 이미지 수정
            Part part = s3Service.extractFile(parts);
            String fileName = s3Service.uploadS3(part, "profile");

            updateDb(user, fileName, myReq.getNickname());
            my.setUserFileUrl(fileName);
        }
        else{                                                               // 프로필 이미지 삭제 -> DB userFileUrl -> null
            updateDb(user, null, myReq.getNickname());
            my.setUserFileUrl(null);
        }
        myRes.setData(my);
        return myRes;
    }

    /**
     * 프로필 DB update
     * @param user : User Model
     * @param savingFileName : 파일 경로 중 파일 이름 part
     * @param nickname : 닉네임
     */
    private void updateDb(Optional<User> user, String savingFileName, String nickname){
        user.ifPresent(selectUser -> {
            selectUser.setFileUrl(savingFileName);
            selectUser.setNickname(nickname);
            userRepository.save(selectUser);
        });
    }
}
