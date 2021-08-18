package com.ssafy.commb.service;

import com.ssafy.commb.dao.KeywordDao;
import com.ssafy.commb.dto.book.KeywordDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.DailyEvent;
import com.ssafy.commb.model.Keyword;
import com.ssafy.commb.repository.DailyEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class KeywordServiceImpl implements KeywordService{

    @Autowired
    private KeywordDao keywordDao;

    /**
     * 각 유저별 검색 키워드 추천
     * @param request : 유저 ID
     * @return : 키워드 추천 리스트
     */
    @Override
    public KeywordDto.ResponseList keywordRecommend(HttpServletRequest request) {

        List<KeywordDto> keywords = keywordDao.getKeywordRecommend((int) request.getAttribute("userId") );
        KeywordDto.ResponseList keywordResList = new KeywordDto.ResponseList();
        keywordResList.setData(keywords);
        return keywordResList;
    }

    /**
     * @ 일일 키워드 이벤트 업데이트
     * @ Scheduler
     */
    @Override
    public void updateKeywordEvent() throws DuplicateKeyException {
        keywordDao.getAndSetRandomKeyword();
    }
}
