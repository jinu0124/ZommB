package com.ssafy.commb.controller;

import com.ssafy.commb.common.QueryStringArgResolver;
import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.book.KeywordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/books")
@Api("Book Controller API V1")
public class BookController {
    static final String url = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F484319%3Ftimestamp%3D20201124225204";

    // /books?searchType=""&searchWord=""
    // 전체 도서 검색
    @GetMapping("")
    @ApiOperation(value="검색 책 리스트(searchType, Word)", response = BookDto.Response.class)
    public ResponseEntity<List<BookDto.Response>> findBookList(@QueryStringArgResolver BookDto.BookSearchRequest bookReq){

        BookDto book = BookDto.builder().id(1).bookName(bookReq.getSearchWord()).author("문성욱").publisher("싸피괴물").year(2021).genre("스릴러")
        .isbn("1234567891234").bookFileUrl(url).readCnt(9999999).rate(3.5f).build();

        BookDto.Response bookRes = new BookDto.Response();
        bookRes.setData(book);

        List<BookDto.Response> books = new ArrayList<>();
        books.add(bookRes);

        return new ResponseEntity<List<BookDto.Response>>(books, HttpStatus.OK);
    }

    // 도서 상세 조회
    @GetMapping("/{bookId}")
    @ApiOperation(value="책 상세 정보 조회", response=BookDto.Response.class)
    public Object findBookList(@PathVariable("bookId") Integer bookId){
        List<KeywordDto> keywordList = new ArrayList<>();
        keywordList.add(KeywordDto.builder().id(1).keyword("키워드").build());

        BookDto book = BookDto.builder().id(bookId).bookName("책이름").publisher("출판").year(2021).
                author("저자").contents("요약 줄거리").genre("스릴러").isbn("1234567891234").bookFileUrl(url).readCnt(4287).
                keyword(keywordList).build();

        BookDto.Response bookRes = new BookDto.Response();
        bookRes.setData(book);

        return new ResponseEntity<BookDto.Response>(bookRes, HttpStatus.OK);
    }
}
