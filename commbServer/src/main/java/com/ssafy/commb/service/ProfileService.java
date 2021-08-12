package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;

public interface ProfileService {
    public MyDto.Response updateProfile(MyDto.ModifyRequest myReq, MultipartHttpServletRequest request) throws IOException, ServletException;

}
