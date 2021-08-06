package com.ssafy.commb.service;

import com.ssafy.commb.dao.KeywordDao;
import com.ssafy.commb.dto.book.KeywordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService{

    @Autowired
    private KeywordDao keywordDao;

    @Override
    public KeywordDto.ResponseList keywordRecommend(HttpServletRequest request) {

        List<KeywordDto> keywords = keywordDao.getKeywordRecommend((int) request.getAttribute("userId") );
        KeywordDto.ResponseList keywordResList = new KeywordDto.ResponseList();
        keywordResList.setData(keywords);
        return keywordResList;
    }
}
