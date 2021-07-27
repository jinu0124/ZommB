package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="HashTag")
@Getter
@Setter
public class HashTag {

    @Id
    @Column(name="feed_id", insertable=false, updatable=false)
    private int id;

    private String tag;

    /* 해시태그가 달린 피드 */
    @ManyToOne
    @JoinColumn(name="feed_id", insertable=false, updatable=false)
    private Feed feed;
}
