package com.ssafy.commb.service;

import com.ssafy.commb.dao.BookDao;
import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.book.GenreDto;
import com.ssafy.commb.dto.bookshelf.BookShelfCntDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.BookShelves;
import com.ssafy.commb.model.BookShelvesId;
import com.ssafy.commb.repository.BookShelvesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookShelvesRepository bookShelvesRepository;

    @Override
    public BookDto.ResponseList getBooksByName(BookDto.BookShelfSearchRequest bookReq, HttpServletRequest request) {

        bookReq.setUserId((int) request.getAttribute("userId"));
        Map<String, Object> map = new HashMap<>();
        map.put("userId", bookReq.getUserId());
        map.put("bookName", bookReq.getBookName());
        map.put("isRead", bookReq.getIsRead());
        List<BookDto> books = bookDao.getBooksByName(map);

        BookDto.ResponseList bookResList = new BookDto.ResponseList();
        GenreDto genre = new GenreDto();
        for(BookDto book : books){
            book.setGenre(genre.getGenre().get(book.getIsbn().substring(10, 13)));
        }

        bookResList.setData(books);
        return bookResList;
    }

    @Override
    public void addMyShelf(BookDto.RegisterRequest bookReq, HttpServletRequest request) {
        Optional<BookShelves> bookShelvesOptional = bookShelvesRepository.findByBookIdAndUserId((int) bookReq.getId(), (int) request.getAttribute("userId"));

        bookShelvesOptional.ifPresent(select -> {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "이미 등록된 도서입니다.");
        });
        BookShelves bookShelves = new BookShelves();

        BookShelvesId bookShelvesId = new BookShelvesId(bookReq.getId(), (int) request.getAttribute("userId"));
        bookShelves.setId(bookShelvesId);
        bookShelves.setIsRead(bookReq.getIsRead());
        bookShelves.setRate(bookReq.getRate());
        bookShelves.setCreateAt(new Date());

        bookShelvesRepository.save(bookShelves);
    }

    @Override
    public BookShelfCntDto.Response getUserReadCnt(int userId) {
        List<Map<String, Object>> list = bookDao.getUserReadCnt(userId);
        BookShelfCntDto bookShelfCnt;
        if(list.size() == 0) bookShelfCnt = BookShelfCntDto.builder().libraryCnt(0).bookcartCnt(0).build();
        else if(list.size() == 1){
            if((boolean) list.get(0).get("isRead")) bookShelfCnt = BookShelfCntDto.builder()
                        .libraryCnt(Math.toIntExact((Long) list.get(0).get("cnt"))).build();
            else bookShelfCnt = BookShelfCntDto.builder()
                    .bookcartCnt(Math.toIntExact((Long) list.get(0).get("cnt"))).build();
        }
        else bookShelfCnt = BookShelfCntDto.builder()
                    .libraryCnt(Math.toIntExact((Long) list.get(0).get("cnt"))).bookcartCnt(Math.toIntExact((Long) list.get(1).get("cnt"))).build();

        BookShelfCntDto.Response bookShelfCntRes = new BookShelfCntDto.Response();
        bookShelfCntRes.setData(bookShelfCnt);

        return bookShelfCntRes;
    }

    @Override
    public void deleteBookInBookShelf(int bookId, HttpServletRequest request) {

        Optional<BookShelves> bookShelves = bookShelvesRepository.findByBookIdAndUserId(bookId, (int) request.getAttribute("userId"));

        bookShelves.ifPresent(bookSelect -> {
            bookShelvesRepository.delete(bookSelect);
        });
    }

    @Override
    public void moveBook(int bookId, HttpServletRequest request) {
        Optional<BookShelves> bookShelves = bookShelvesRepository.findByBookIdAndUserId(bookId, (int) request.getAttribute("userId"));
        if(!bookShelves.isPresent()) throw new ApplicationException(HttpStatus.GONE, "해당 도서를 DB로부터 찾을 수 없습니다.");
        bookShelves.ifPresent(bookSelect -> {
            bookSelect.setIsRead(Math.abs(bookSelect.getIsRead() - 1));             // toggle

            bookShelvesRepository.save(bookSelect);
        });
    }

    @Override
    public BookDto.ResponseList getTopBooks(int userId) {
        List<BookDto> books = bookDao.getTopBooks(userId);

        BookDto.ResponseList bookResList = new BookDto.ResponseList();
        bookResList.setData(books);
        return bookResList;
    }

    @Override
    public void addBookTop(BookDto.TopBarRegisterRequest bookReq, HttpServletRequest request) {
        int cnt = bookDao.getBookByUserIdAndBookId(bookReq.getId(), (int) request.getAttribute("userId"));
        if(cnt == 0) throw new ApplicationException(HttpStatus.BAD_REQUEST, "서재에 존재하지 않는 책입니다.");
        try{
            bookDao.addBookTop(bookReq.getId(), (int) request.getAttribute("userId"));
        }catch(Exception e){
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "DB에 중복 키가 존재하여 insert 작업을 할 수 없습니다.");
        }
    }

    @Override
    public void deleteAllBookTop(HttpServletRequest request) {
        bookDao.deleteAllBookTop((int) request.getAttribute("userId"));
    }

    @Override
    public void deleteBookTop(int bookId, HttpServletRequest request) {
        if(bookDao.deleteBookTop(bookId, (int) request.getAttribute("userId")) != 1)
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, bookId + " 도서가 삭제되지 않았습니다.");

    }


}
