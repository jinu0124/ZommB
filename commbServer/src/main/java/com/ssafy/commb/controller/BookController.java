package com.ssafy.commb.controller;

import com.ssafy.commb.common.QueryStringArgResolver;
import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value="/api/books")
@Api("Book Controller API V1")
public class BookController {
    static final String url = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F484319%3Ftimestamp%3D20201124225204";

    @Autowired
    BookService bookService;

    // 전체 도서 검색
    @GetMapping("")
    @ApiOperation(value="검색 책 리스트(searchType, Word)", response = BookDto.Response.class)
    public ResponseEntity<BookDto.ResponseList> findBookList(@QueryStringArgResolver BookDto.BookSearchRequest bookReq) throws IOException {


        return new ResponseEntity<BookDto.ResponseList>(bookService.findBookList(bookReq), HttpStatus.OK);
    }

    // 도서 상세 조회
    @GetMapping("/{bookId}")
    @ApiOperation(value="책 상세 정보 조회", response=BookDto.Response.class)
    public Object findBook(@PathVariable("bookId") Integer bookId) throws IOException {
        return new ResponseEntity<BookDto.Response>(bookService.findBook(bookId), HttpStatus.OK);
    }
}
