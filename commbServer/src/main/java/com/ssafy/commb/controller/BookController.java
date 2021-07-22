package com.ssafy.commb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/books")
public class BookController {

    @GetMapping("")
    public Object findBookList(){
        return null;
    }

    @GetMapping("/{bookId}")
    public Object findBookList(
            @PathVariable("bookId") Integer bookId
    ){
        return null;
    }
}
