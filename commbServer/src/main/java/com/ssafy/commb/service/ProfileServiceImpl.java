package com.ssafy.commb.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProfileServiceImpl implements ProfileService {

    /* for production after build code */
//    static String uploadPath;

    /* for eclipse development before build code */
//    static String uploadPath = "C:" + File.separator + "Users" + File.separator + "jinwoo"
//            + File.separator + "IdeaProjects"
//            + File.separator + "SUBPJT1"
//            + File.separator + "commbServer"
//            + File.separator + "src"
//            + File.separator + "main"
//            + File.separator + "resources"
//            + File.separator + "static";


    @Value("${cloud.profile}")
    private String awsProfileUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    private static final String uploadFolder = "upload";

    @Transactional
    @Override
    public MyDto.Response updateProfile(MyDto.ModifyRequest myReq, MultipartHttpServletRequest request) throws IOException, ServletException {
        int userId = Integer.parseInt(String.valueOf(request.getAttribute("userId")));              // jwt ???????????? ????????? userId
        MyDto.Response myRes = new MyDto.Response();
        Collection<Part> parts = request.getParts();
//        uploadPath = request.getSession().getServletContext().getRealPath("/");               // ?????? ???

        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()) throw new ApplicationException(HttpStatus.GONE, "???????????? ?????? ??????");
        MyDto my = MyDto.builder().userFileUrl(user.get().getFileUrl()).nickname(myReq.getNickname()).id(user.get().getId()).build();

        // ?????? ?????? ?????? ?????? : DB?????? ?????? ????????? ?????? ?????? ???????????? ?????? ?????? ????????????
        if(myReq.getFlag() > 2) throw new ApplicationException(HttpStatus.BAD_REQUEST, "????????? ???????????????.");
        else if(myReq.getFlag() != 0){
            // Project ??? ?????? ??????
//            File file = new File(uploadPath + File.separator + uploadFolder + File.separator + user.get().getFileUrl());
//            if(file.exists()) if(!file.delete()) throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "???????????? ?????? ?????? ??????");

            // S3 ?????? ??????
            if(my.getUserFileUrl() != null) {
                s3Service.deleteS3(my.getUserFileUrl(), "profile");
//                s3Client.deleteObject(this.bucket, "profile/" + my.getUserFileUrl());
            }
        }

        if(myReq.getFlag() == 0) {                                          // ????????? ????????? ??????
            updateDb(user, user.get().getFileUrl(), myReq.getNickname());
            if(my.getUserFileUrl() != null) my.setUserFileUrl(awsProfileUrl + user.get().getFileUrl());
            else my.setUserFileUrl(null);
        }
        else if(myReq.getFlag() == 1) {                                      // ????????? ????????? ??????
            // S3 ?????? ????????? ??? ?????? ?????? ????????????
            Part part = s3Service.extractFile(parts);
            String fileName = s3Service.uploadS3(part, "profile");

            updateDb(user, fileName, myReq.getNickname());
            my.setUserFileUrl(awsProfileUrl + fileName);
        }
        else{                                                               // ????????? ????????? ?????? -> DB userFileUrl -> null
            updateDb(user, null, myReq.getNickname());
            my.setUserFileUrl(null);
        }
        myRes.setData(my);
        return myRes;
    }

    private void updateDb(Optional<User> user, String savingFileName, String nickname){
        // DB ????????? ????????????
        user.ifPresent(selectUser -> {
            selectUser.setFileUrl(savingFileName);
            selectUser.setNickname(nickname);
            userRepository.save(selectUser);
        });
    }



    private String fileUpload(String uploadPath, Part part, String fileName) throws IOException {
        // ?????? ????????? ??????
        File uploadDir = new File(uploadPath + File.separator +  uploadFolder);   // File upload Path
        if(!uploadDir.exists()) if(!uploadDir.mkdir()) throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "??? ???????????? ?????? ??????");

        part.write(uploadPath + File.separator + uploadFolder + File.separator + fileName);

        return fileName;

//        for(Part part : parts){
//            String fileName = getFileName(part);
//
//            if("".equals(fileName)) continue;           // filename ??? ???????????? ???????????? continue -> ?????? part ?????? -> file?????? ??????
//
//            UUID uuid = UUID.randomUUID();
//            String extension = FilenameUtils.getExtension(fileName);
//            String date = sDate.format(new Date());
//
//            // ?????? ?????? ?????? ??????
//            savingFileName = uuid + date + "." + extension;
//
//            part.write(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
//            return savingFileName;
//        }
    }
}
