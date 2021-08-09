package com.ssafy.commb.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Feed")
@Getter
@Setter
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    private String content;

    private int blocked;

    private String fileUrl;

    /* 피드를 작성한 유저 */
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    /* 피드의 주제인 책 */
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    /* 피드에 좋아요를 누른 유저 */
//    @ManyToMany
//    @JoinTable(name="Thumb", joinColumns = @JoinColumn(name="feed_id")
//            , inverseJoinColumns = @JoinColumn(name="user_id"))
//    private List<User> thumbsUsers = new ArrayList<User>();

    // User 쓰면 Failed to initialize JPA EntityManagerFactory mappedBy reference an unknown target entity property
    // Feed Entity 안에 User Entity가 있어서 error 나는 것 같음 -> Thumb로 변경
    @OneToMany(mappedBy = "feed")
    private List<Thumb> thumbsUser = new ArrayList<>();

    /* 피드에 달린 댓글 */
    @OneToMany(mappedBy = "feed")
    private List<Comment> comments = new ArrayList<>();

    /* 피드에 달린 해시태그 */
    @OneToMany(mappedBy = "feed")
    private List<HashTag> hashTags = new ArrayList<HashTag>();


}
