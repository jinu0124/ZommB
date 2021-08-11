package com.ssafy.commb.model;

import com.ssafy.commb.dto.book.KeywordDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Keyword")
@Getter
@Setter
@ToString
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String keyword;

    /* 유저키워드 */
    @OneToMany(mappedBy = "keyword")
    private List<UserKeyword> userKeywords = new ArrayList<UserKeyword>();

    @OneToMany(mappedBy = "keyword")
    private List<DailyEvent> dailyEvents = new ArrayList<DailyEvent>();

    @ManyToMany(mappedBy = "keywords", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Keyword(int id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public KeywordDto convertKeywordDto(){
        return KeywordDto.builder()
                .id(this.id)
                .keyword(this.keyword)
                .build();
    }
}
