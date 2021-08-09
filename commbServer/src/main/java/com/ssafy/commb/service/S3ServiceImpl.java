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
import com.ssafy.commb.exception.ApplicationException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service{

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private AmazonS3 s3Client;

    @Autowired
    private ProfileService profileService;

    // S3 키를 통한 인증
    @PostConstruct
    public void setS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    // S3 파일 삭제
    public void deleteS3(String url, String dir){
        if(url == null) return;
        s3Client.deleteObject(this.bucket, dir + "/" + url);
    }

    // AWS S3 파일 업로드
    public String uploadS3(Part file, String dir) throws IOException {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + getFileName(file);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(Mimetypes.getInstance().getMimetype(fileName));

        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
        objectMetadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

        s3Client.putObject(new PutObjectRequest(bucket,  dir + "/" + fileName, byteArrayIs, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));        // public read 권한 주기

        // s3Client.getUrl(bucket, fileName).toString()
        return fileName;
    }

    public Part extractFile(Collection<Part> parts) throws IOException {
        for(Part part : parts){
            String fileName = getFileName(part);

            if("".equals(fileName)) continue;           // filename 이 추출되지 않았다면 continue -> 다음 part 탐색 -> file이름 찾기

            return part;
        }
        throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 물리 이미지 업로드 실패");
    }

    public String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "";
    }
}
