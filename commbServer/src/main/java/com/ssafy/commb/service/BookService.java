package com.ssafy.commb.service;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.bookshelf.BookShelfCntDto;

import javax.servlet.http.HttpServletRequest;

public interface BookService {
    public BookDto.ResponseList getBooksByName(BookDto.BookShelfSearchRequest bookReq, HttpServletRequest request);

    public void addMyShelf(BookDto.RegisterRequest bookReq, HttpServletRequest request);

    public BookShelfCntDto.Response getUserReadCnt(int userId);

    public void deleteBookInBookShelf(int bookId, HttpServletRequest request);

    public void moveBook(int bookId, HttpServletRequest request);

    public BookDto.ResponseList getTopBooks(int userId);

    public void addBookTop(BookDto.TopBarRegisterRequest bookReq, HttpServletRequest request);

    public void deleteAllBookTop(HttpServletRequest request);

    public void deleteBookTop(int bookId, HttpServletRequest request);
}
