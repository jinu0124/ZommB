package com.ssafy.commb.service;

import com.ssafy.commb.dao.BookDao;
import com.ssafy.commb.dto.book.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Override
    public BookDto.ResponseList getBooksByName(BookDto.BookShelfSearchRequest bookReq, HttpServletRequest request) {

        int userId = (int) request.getAttribute("userId");
        bookReq.setUserId(userId);
        List<BookDto> books = bookDao.getBooksByName(bookReq);

        return null;
    }
}
