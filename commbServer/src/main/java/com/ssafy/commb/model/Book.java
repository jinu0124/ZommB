package com.ssafy.commb.model;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.model.follow.Follow;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Book")
@Getter
@Setter
@AllArgsConstructor                         // Builder pattern 사용 시 반드시 전체 인자를 포함하는 생성자 필수
@NoArgsConstructor                          // 기본 생성자
@Builder                                    // Builder 패턴 사용
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bookName;

    private String author;

    private Integer year;

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
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(name="book_keyword", joinColumns = @JoinColumn(name="book_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name="keyword_id", referencedColumnName = "id"))
    private List<Keyword> keywords = new ArrayList<Keyword>();

    public void addKeyword(Keyword keyword){
        this.keywords.add(keyword);
        keyword.getBooks().add(this);
    }

    public BookDto convertBookDto(){
        return BookDto.builder()
                .id(this.id)
                .bookName(this.bookName)
                .bookFileUrl(this.fileUrl)
                .author(this.author)
                .contents(this.description)
                .isbn(this.isbn)
                .publisher(this.publisher)
                .year(this.year)
                .build();

    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o==null || getClass() != o.getClass()){
            return false;
        }

        Book book = (Book) o;

        return Objects.equals(this.isbn, book.getIsbn());
    }

    @Override
    public int hashCode(){
        return this.isbn.hashCode();
    }

    @Override
    public String toString(){
        return this.isbn;
    }
}
