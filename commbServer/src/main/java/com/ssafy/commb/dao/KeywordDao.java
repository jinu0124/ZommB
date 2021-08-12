package com.ssafy.commb.dao;

import com.ssafy.commb.dto.book.KeywordDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordDao {
    public List<KeywordDto> getKeywordRecommend(int userId);

    public int getAndSetRandomKeyword();
}
