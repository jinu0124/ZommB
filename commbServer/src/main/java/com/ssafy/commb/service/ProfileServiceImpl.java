package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
//        int userId = 10000001;            // 테스트용
        MyDto.Response myRes = new MyDto.Response();

        Collection<Part> parts = request.getParts();                // Multipart parts 받아오기

        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()) return null;
        MyDto my = MyDto.builder().userFileUrl(user.get().getFileUrl()).nickname(myReq.getNickname()).id(user.get().getId()).build();

        // 기존 물리 파일 삭제 : DB에서 기존 파일의 물리 경로 가져와서 물리 파일 삭제하기
        if(myReq.getFlag() != 0){                                   // 프로필 이미지 삭제 or 새 프로필 이미지 업로드 시
            File file = new File(uploadPath + File.separator + user.get().getFileUrl());
            if(file.exists()) file.delete();
        }

        try{
            if(myReq.getFlag() == 0) {                                          // 프로필 이미지 유지
                user.ifPresent(selectUser -> {
                    selectUser.setNickname(myReq.getNickname());
                    userRepository.save(selectUser);
                });
                myRes.setData(my);
                return myRes;
            }
            else if(myReq.getFlag() == 1) {                                      // 프로필 이미지 수정
                my.setUserFileUrl(fileUpload(uploadPath, parts, myReq.getNickname(), user));
                if(updateDb(user, my.getUserFileUrl(), myReq.getNickname())) {
                    myRes.setData(my);
                    return myRes;
                }
            }
            else{                                                               // 프로필 이미지 삭제 -> DB userFileUrl -> null
                if(updateDb(user, null, myReq.getNickname())){
                    my.setUserFileUrl(null);
                    myRes.setData(my);
                    return myRes;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private boolean updateDb(Optional<User> user, String savingFileName, String nickname){
        if("failed".equals(savingFileName)) return false;
        // DB 프로필 업데이트
        user.ifPresent(selectUser -> {
            selectUser.setFileUrl(savingFileName);
            selectUser.setNickname(nickname);
            userRepository.save(selectUser);
        });
        return true;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "";
    }

    private String fileUpload(String uploadPath, Collection<Part> parts, String nickname,  Optional<User> user) {
        // 파일 업로드 작업
        File uploadDir = new File(uploadPath + File.separator +  uploadFolder);   // File upload Path
        if(!uploadDir.exists()) uploadDir.mkdir();

        String savingFileName = null;
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        for(Part part : parts){
            String fileName = getFileName(part);

            if("".equals(fileName)) continue;           // filename 이 추출되지 않았다면 continue -> 다음 part 탐색 -> file이름 찾기

            UUID uuid = UUID.randomUUID();
            String extension = FilenameUtils.getExtension(fileName);
            String date = sDate.format(new Date());

            // 물리 파일 쓰기 작업
            savingFileName = uuid + date + "." + extension;
            try {
                part.write(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "failed";
            }
        }
        return savingFileName;
    }
}
