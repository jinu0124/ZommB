package com.ssafy.commb.service;

import com.ssafy.commb.dto.book.KeywordDto;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;

public interface KeywordService {
    public KeywordDto.ResponseList keywordRecommend(HttpServletRequest request);

    public void updateKeywordEvent() throws SQLIntegrityConstraintViolationException;

}
