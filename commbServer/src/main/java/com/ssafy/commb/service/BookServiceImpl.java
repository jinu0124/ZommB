package com.ssafy.commb.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.commb.dao.BookDao;
import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.book.GenreDto;
import com.ssafy.commb.dto.bookshelf.BookShelfCntDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.exception.book.NotFoundBookException;
import com.ssafy.commb.model.*;
import com.ssafy.commb.repository.BookShelvesRepository;
import com.ssafy.commb.dto.book.KakaoSearchBookResponseDto;
import com.ssafy.commb.repository.BookRepository;
import com.ssafy.commb.util.KakaoSearchAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookShelvesRepository bookShelvesRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    KakaoSearchAPI kakaoSearchAPI;

    @PersistenceContext
    private EntityManager em;

    @Override
    public BookDto.ResponseList getBooksByName(BookDto.BookShelfSearchRequest bookReq, HttpServletRequest request) {

        bookReq.setUserId((int) request.getAttribute("userId"));
        Map<String, Object> map = new HashMap<>();
        map.put("userId", bookReq.getUserId());
        map.put("bookName", bookReq.getBookName());
        map.put("isRead", bookReq.getIsRead());
        List<BookDto> books = bookDao.getBooksByName(map);

        BookDto.ResponseList bookResList = new BookDto.ResponseList();
//        GenreDto genre = new GenreDto();
//        for(BookDto book : books){
//            book.setGenre(genre.getGenre().get(book.getIsbn().substring(10, 12)));        // isbn 장르 -> isbn에는 장르 정보가 없따
//        }

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
        if(!bookReq.getRate().isNaN()) bookShelves.setRate(bookReq.getRate());
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
        bookDao.deleteBookTop(bookId, (int) request.getAttribute("userId"));        // Top Bar 동기화
    }

    // 서재 <-> 북카트
    @Override
    public void moveBook(int bookId, double rate, HttpServletRequest request) {
        Optional<BookShelves> bookShelves = bookShelvesRepository.findByBookIdAndUserId(bookId, (int) request.getAttribute("userId"));
        if(!bookShelves.isPresent()) throw new ApplicationException(HttpStatus.GONE, "해당 도서를 DB로부터 찾을 수 없습니다.");
        bookShelves.ifPresent(bookSelect -> {
            if(bookSelect.getIsRead() == 1) bookSelect.setRate(new BookShelves().getRate());
            else bookSelect.setRate(rate);
            bookSelect.setIsRead(Math.abs(bookSelect.getIsRead() - 1));             // toggle

            bookShelvesRepository.save(bookSelect);
            if(bookSelect.getIsRead() == 0) bookDao.deleteBookTop(bookId, (int) request.getAttribute("userId"));        // Top Bar 동기화
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



    /**
     * 
     * @param bookReq : 검색DTO
     * @return 책 리스트
     * @throws IOException
     */
    public BookDto.ResponseList findBookList(BookDto.BookSearchRequest bookReq) throws IOException {

        // 카카오 책 검색 API
        KakaoSearchBookResponseDto kakaoSearchBookResponseDto = kakaoSearchAPI.search(bookReq);

        List<Book> existBooks = new ArrayList<>();
        Set<Book> saveBooks = kakaoSearchBookResponseDto.getDocuments()
                .stream()
                .filter(document -> {
                    Optional<Book> book =bookRepository.findByIsbn(document.getIsbn13());
                    // 이미 존재하는 책인 경우
                    if(book.isPresent()){
                        existBooks.add(book.get());
                    }
                    return !book.isPresent();
                })
                .map(KakaoSearchBookResponseDto.Document::convertBook)
                .collect(Collectors.toSet());
        
        // DB 저장
        List<Book> books = new ArrayList<>(saveBooks);
        books = bookRepository.saveAllAndFlush(new ArrayList<>(books));

        books.addAll(existBooks);

        // 반환 평점 및 읽은 사람 수 반환
        return BookDto.ResponseList.builder()
                .data(books.stream()
                        .map(book -> {
                            BookDto bookDto = book.convertBookDto();
                            // 평점
                            bookDto.setRate(getBookRate(book));
                            // 읽은 사람 수
                            bookDto.setReadCnt(getBookReadCnt(book));
                            return bookDto;
                        }).collect(Collectors.toList())
                )
                .build();
    }

    /**
     * 
     * @param targetBook : 찾을 대상 책
     * @return 책의 평균 평점
     */
    public Double getBookRate(Book targetBook){
        QBookShelves qBookShelves = QBookShelves.bookShelves;
        JPAQueryFactory qf = new JPAQueryFactory(em);

        JPAQuery<Double> query = qf.from(qBookShelves)
                .groupBy(qBookShelves.book)
                .where(qBookShelves.book.eq(targetBook))
                .where(qBookShelves.isRead.eq(1))
                .select(qBookShelves.rate.avg());
        if(query.fetchOne() == null) return 0.0;
        return query.fetchOne();
    }

    /**
     * 
     * @param targetBook : 찾을 대상 책
     * @return 책을 읽은 사람 수
     */
    public Integer getBookReadCnt(Book targetBook){
        QBookShelves qBookShelves = QBookShelves.bookShelves;
        JPAQueryFactory qf = new JPAQueryFactory(em);

        JPAQuery<Long> query = qf.from(qBookShelves)
                .groupBy(qBookShelves.book)
                .where(qBookShelves.book.eq(targetBook))
                .where(qBookShelves.isRead.eq(1))
                .select(qBookShelves.user.count());
        if(query.fetchOne() == null) return 0;
        return query.fetchOne().intValue();
    }

    /**
     *
     * @param bookId : 찾을 책 Id
     * @return : 책 상세정보 DTO
     */
    public BookDto.Response findBook(int bookId){
        Book book = bookRepository.getById(bookId);

        if(book == null) throw new NotFoundBookException();

        BookDto bookDto = book.convertBookDto();

        bookDto.setReadCnt(getBookReadCnt(book));
        bookDto.setRate(getBookRate(book));
        bookDto.setKeywords(book.getKeywords().stream()
                .map(Keyword::convertKeywordDto)
                .collect(Collectors.toList()));
        return BookDto.Response.builder()
                .data(bookDto)
                .build();
    }
}
