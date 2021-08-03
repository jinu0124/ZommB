package com.ssafy.commb.service;

import com.ssafy.commb.dto.feed.FeedDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;

public interface FeedService {
    public FeedDto uploadFeed(FeedDto.RegisterRequest feedReq, MultipartHttpServletRequest request) throws IOException, ServletException;
}
