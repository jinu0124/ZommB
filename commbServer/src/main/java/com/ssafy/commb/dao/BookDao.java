package com.ssafy.commb.dao;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.bookshelf.BookShelfCntDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookDao {
    public List<BookDto> getBooksByName(Map<String, Object> map);

    public List<Map<String, Object>> getUserReadCnt(int userId);

    public List<BookDto> getTopBooks(int userId);

    public int getBookByUserIdAndBookId(int bookId, int userId);

    public int addBookTop(int bookId, int userId);

    public void deleteAllBookTop(int userId);

    public int deleteBookTop(int bookId, int userId);

}
