package com.ssafy.commb.service;

import com.ssafy.commb.dao.KeywordDao;
import com.ssafy.commb.dto.book.KeywordDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.DailyEvent;
import com.ssafy.commb.model.Keyword;
import com.ssafy.commb.repository.DailyEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class KeywordServiceImpl implements KeywordService{

    @Autowired
    private KeywordDao keywordDao;

    @Autowired
    private DailyEventRepository dailyEventRepository;

    @Override
    public KeywordDto.ResponseList keywordRecommend(HttpServletRequest request) {

        List<KeywordDto> keywords = keywordDao.getKeywordRecommend((int) request.getAttribute("userId") );
        KeywordDto.ResponseList keywordResList = new KeywordDto.ResponseList();
        keywordResList.setData(keywords);
        return keywordResList;
    }

    @Override
    public void updateKeywordEvent() {
        if( keywordDao.getAndSetRandomKeyword() != 1){
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "일간 키워드 업데이트 배치작업 실패");
        };
    }
}
