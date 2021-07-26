package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Book")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bookName;

    private String author;

    private int year;

    private String isbn;

    private String fileUrl;

    private String publisher;

    private String description;

    /* 책과 피드는 일대다 관계 */
    @OneToMany(mappedBy = "book")
    private List<Feed> feeds = new ArrayList<Feed>();

    @OneToMany(mappedBy = "book")
    private List<BookShelves> bookShelves = new ArrayList<BookShelves>();

    @OneToMany(mappedBy = "book")
    private List<WeeklyEvent> weeklyEvents = new ArrayList<WeeklyEvent>();


    // 도서 키워드
    @ManyToMany
    @JoinTable(name="Book_Keyword", joinColumns = @JoinColumn(name="book_id")
            , inverseJoinColumns = @JoinColumn(name="keyword_id"))
    private List<Keyword> keywords = new ArrayList<Keyword>();
}
