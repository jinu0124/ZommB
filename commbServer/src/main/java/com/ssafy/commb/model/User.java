package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="User")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String name;

    private String nickname;

    private String password;

    private int year;

    private char gender;

    private String role;

    private int bookmark;

    private int pencil;

    private int bookmarkOn;

    private int pencilOn;

    private String fileUrl;

    /* 유저의 피드 */
    @OneToMany(mappedBy = "user")
    private List<Feed> feeds = new ArrayList<Feed>();

    /* 유저의 서재/북카트 */
    @OneToMany(mappedBy = "user")
    private List<BookShelves> bookShelves = new ArrayList<BookShelves>();

    /* 유저의 주간 이벤트 참여 */
    @OneToMany(mappedBy = "user")
    private List<WeeklyEventParticipate> weeklyEventParticipates = new ArrayList<WeeklyEventParticipate>();

    /* 유저의 상단도서 */
    @ManyToMany
    @JoinTable(name="Top_Book", joinColumns = @JoinColumn(name="user_id")
            , inverseJoinColumns = @JoinColumn(name="comment_id"))
    private List<Book> topBooks = new ArrayList<Book>();

    @OneToMany(mappedBy = "user")
    private List<UserKeyword> userKeywords = new ArrayList<UserKeyword>();

    @OneToMany(mappedBy = "user")
    private List<DailyEventParticipate> dailyEventParticipates = new ArrayList<DailyEventParticipate>();
}
