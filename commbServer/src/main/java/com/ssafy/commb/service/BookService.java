package com.ssafy.commb.service;

import com.ssafy.commb.dto.book.BookDto;

import javax.servlet.http.HttpServletRequest;

public interface BookService {
    public BookDto.ResponseList getBooksByName(BookDto.BookShelfSearchRequest bookReq, HttpServletRequest request);
}
