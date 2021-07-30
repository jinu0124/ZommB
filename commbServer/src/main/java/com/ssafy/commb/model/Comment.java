package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    private int isMod;

    /* 댓글을 작성한 유저 */
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    /* 댓글이 달린 피드 */
    @ManyToOne
    @JoinColumn(name="feed_id")
    private Feed feed;

    /* 댓글 좋아요를 누른 유저 */
    @ManyToMany
    @JoinTable(name="Comment_Thumb", joinColumns = @JoinColumn(name="comment_id")
            , inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> thumbUsers = new ArrayList<User>();
}
