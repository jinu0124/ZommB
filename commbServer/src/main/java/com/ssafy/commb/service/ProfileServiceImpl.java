package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProfileServiceImpl implements ProfileService {

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
    private UserRepository userRepository;

    private static final String uploadFolder = "upload";

    @Transactional
    @Override
    public MyDto.Response updateProfile(MyDto.ModifyRequest myReq, MultipartHttpServletRequest request) throws IOException, ServletException {
        int userId = (int) request.getAttribute("userId");              // jwt 토큰으로 인증된 userId
        MyDto.Response myRes = new MyDto.Response();
        Collection<Part> parts = request.getParts();

        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()) throw new ApplicationException("회원정보 조회 실패", HttpStatus.GONE);
        MyDto my = MyDto.builder().userFileUrl(user.get().getFileUrl()).nickname(myReq.getNickname()).id(user.get().getId()).build();

        // 기존 물리 파일 삭제 : DB에서 기존 파일의 물리 경로 가져와서 물리 파일 삭제하기
        if(myReq.getFlag() > 2) throw new ApplicationException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        else if(myReq.getFlag() != 0){
            File file = new File(uploadPath + File.separator + user.get().getFileUrl());
            if(file.exists()) if(!file.delete()) throw new ApplicationException("디렉토리 파일 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(myReq.getFlag() == 0) {                                          // 프로필 이미지 유지
            updateDb(user, user.get().getFileUrl(), myReq.getNickname());
            my.setUserFileUrl(user.get().getFileUrl());
        }
        else if(myReq.getFlag() == 1) {                                      // 프로필 이미지 수정
            my.setUserFileUrl(fileUpload(uploadPath, parts));
            updateDb(user, my.getUserFileUrl(), myReq.getNickname());
        }
        else{                                                               // 프로필 이미지 삭제 -> DB userFileUrl -> null
            updateDb(user, null, myReq.getNickname());
            my.setUserFileUrl(null);
        }
        myRes.setData(my);
        return myRes;
    }

    private void updateDb(Optional<User> user, String savingFileName, String nickname){
        // DB 프로필 업데이트
        user.ifPresent(selectUser -> {
            selectUser.setFileUrl(savingFileName);
            selectUser.setNickname(nickname);
            userRepository.save(selectUser);
        });
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "";
    }

    private String fileUpload(String uploadPath, Collection<Part> parts) throws IOException {
        // 파일 업로드 작업
        File uploadDir = new File(uploadPath + File.separator +  uploadFolder);   // File upload Path
        if(!uploadDir.exists()) if(!uploadDir.mkdir()) throw new ApplicationException("새 디렉토리 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);

        String savingFileName;
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        for(Part part : parts){
            String fileName = getFileName(part);

            if("".equals(fileName)) continue;           // filename 이 추출되지 않았다면 continue -> 다음 part 탐색 -> file이름 찾기

            UUID uuid = UUID.randomUUID();
            String extension = FilenameUtils.getExtension(fileName);
            String date = sDate.format(new Date());

            // 물리 파일 쓰기 작업
            savingFileName = uuid + date + "." + extension;

            part.write(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
            return savingFileName;
        }
        throw new ApplicationException("프로필 물리 이미지 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
