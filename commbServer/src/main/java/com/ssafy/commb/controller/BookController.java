package com.ssafy.commb.controller;

import com.ssafy.commb.dto.book.BookDto;
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

    // 예시 하나만 만들어 둠.
    @GetMapping("")
    @ApiOperation(value="검색 책 리스트", response = BookDto.Response.class)
    public ResponseEntity<List<BookDto.Response>> findBookList(@RequestParam BookDto.Request bookReq){

        BookDto book = BookDto.builder().id(1).bookName("성욱이의 고단한 하루").author("문성욱").publisher("싸피괴물").year(2021).genre("스릴러")
        .isbn("1234567891230").bookFileUrl("2팀").readCnt(9999999).rate(3.5f).build();

        BookDto.Response bookRes = new BookDto.Response();
        bookRes.setData(book);

        List<BookDto.Response> books = new ArrayList<>();
        books.add(bookRes);

        return new ResponseEntity<List<BookDto.Response>>(books, HttpStatus.OK);
    }


    @GetMapping("/{bookId}")
    public Object findBookList(@PathVariable("bookId") Integer bookId){

        return new ResponseEntity<BookDto.Response>((BookDto.Response) null, HttpStatus.OK);
    }
}
