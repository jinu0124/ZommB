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

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private UserRepository userRepository;

    private static final String uploadFolder = "upload";

    private AmazonS3 s3Client;

    // S3 키를 통한 인증
    @PostConstruct
    public void setS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    // AWS S3 파일 업로드
    public String upload(Part file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + getFileName(file);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(Mimetypes.getInstance().getMimetype(fileName));

        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
        objectMetadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

        s3Client.putObject(new PutObjectRequest(bucket, "profile/" + fileName, byteArrayIs, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));        // public read 권한 주기

        // s3Client.getUrl(bucket, fileName).toString()
        return fileName;
    }

    private Part extractFile(Collection<Part> parts) throws IOException {
        for(Part part : parts){
            String fileName = getFileName(part);

            if("".equals(fileName)) continue;           // filename 이 추출되지 않았다면 continue -> 다음 part 탐색 -> file이름 찾기

            return part;
        }
        throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 물리 이미지 업로드 실패");
    }

    @Transactional
    @Override
    public MyDto.Response updateProfile(MyDto.ModifyRequest myReq, MultipartHttpServletRequest request) throws IOException, ServletException {
        int userId = (int) request.getAttribute("userId");              // jwt 토큰으로 인증된 userId
        MyDto.Response myRes = new MyDto.Response();
        Collection<Part> parts = request.getParts();
//        uploadPath = request.getSession().getServletContext().getRealPath("/");               // 빌드 시

        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()) throw new ApplicationException(HttpStatus.GONE, "회원정보 조회 실패");
        MyDto my = MyDto.builder().userFileUrl(user.get().getFileUrl()).nickname(myReq.getNickname()).id(user.get().getId()).build();

        // 기존 물리 파일 삭제 : DB에서 기존 파일의 물리 경로 가져와서 물리 파일 삭제하기
        if(myReq.getFlag() > 2) throw new ApplicationException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        else if(myReq.getFlag() != 0){
            // Project 내 파일 삭제
//            File file = new File(uploadPath + File.separator + uploadFolder + File.separator + user.get().getFileUrl());
//            if(file.exists()) if(!file.delete()) throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "디렉토리 파일 삭제 실패");

            // S3 파일 삭제
            if(my.getUserFileUrl() != null) s3Client.deleteObject(this.bucket, "profile/" + my.getUserFileUrl());
        }

        if(myReq.getFlag() == 0) {                                          // 프로필 이미지 유지
            updateDb(user, user.get().getFileUrl(), myReq.getNickname());
            if(my.getUserFileUrl() != null) my.setUserFileUrl("https://s3.ap-northeast-2.amazonaws.com/ssafy.commb/profile/" + user.get().getFileUrl());
            else my.setUserFileUrl(null);
        }
        else if(myReq.getFlag() == 1) {                                      // 프로필 이미지 수정
            // S3 파일 업로드 및 저장 경로 가져오기
            Part part = extractFile(parts);
            String fileName = upload(part);

            updateDb(user, fileName, myReq.getNickname());
            my.setUserFileUrl("https://s3.ap-northeast-2.amazonaws.com/ssafy.commb/profile/" + fileName);
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

    private String fileUpload(String uploadPath, Part part, String fileName) throws IOException {
        // 파일 업로드 작업
        File uploadDir = new File(uploadPath + File.separator +  uploadFolder);   // File upload Path
        if(!uploadDir.exists()) if(!uploadDir.mkdir()) throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "새 디렉토리 생성 실패");

        part.write(uploadPath + File.separator + uploadFolder + File.separator + fileName);

        return fileName;

//        for(Part part : parts){
//            String fileName = getFileName(part);
//
//            if("".equals(fileName)) continue;           // filename 이 추출되지 않았다면 continue -> 다음 part 탐색 -> file이름 찾기
//
//            UUID uuid = UUID.randomUUID();
//            String extension = FilenameUtils.getExtension(fileName);
//            String date = sDate.format(new Date());
//
//            // 물리 파일 쓰기 작업
//            savingFileName = uuid + date + "." + extension;
//
//            part.write(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
//            return savingFileName;
//        }
    }
}
