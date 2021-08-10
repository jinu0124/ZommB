package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Hashtag")
@Getter
@Setter
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tag;

    /* 해시태그가 달린 피드 */
    @ManyToOne
    @JoinColumn(name="feed_id")
    private Feed feed;
}
