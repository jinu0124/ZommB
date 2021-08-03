package com.ssafy.commb.dao;

import com.ssafy.commb.dto.book.BookDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookDao {
    public List<BookDto> getBooksByName(BookDto.BookShelfSearchRequest bookReq);

}
