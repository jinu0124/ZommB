package com.ssafy.commb.model;


import lombok.Getter;
import lombok.Setter;

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
    @ManyToMany
    @JoinTable(name="Thumb", joinColumns = @JoinColumn(name="feed_id")
            , inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> thumbsUsers = new ArrayList<User>();

    /* 피드에 달린 댓글 */
    @OneToMany(mappedBy = "feed")
    private List<Comment> comments = new ArrayList<Comment>();

    /* 피드에 달린 해시태그 */
    @OneToMany(mappedBy = "feed")
    private List<HashTag> hashTags = new ArrayList<HashTag>();


}
