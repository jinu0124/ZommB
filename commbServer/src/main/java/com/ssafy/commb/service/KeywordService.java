package com.ssafy.commb.service;

import com.ssafy.commb.dto.book.KeywordDto;

import javax.servlet.http.HttpServletRequest;

public interface KeywordService {
    public KeywordDto.ResponseList keywordRecommend(HttpServletRequest request);
}
