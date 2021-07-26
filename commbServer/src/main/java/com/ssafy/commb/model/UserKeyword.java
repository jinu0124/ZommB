package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="User_Keyword")
@Getter
@Setter
public class UserKeyword {

    @EmbeddedId
    private UserKeywordId id;

    private int cnt;

    @ManyToOne
    @JoinColumn(name="keyword_id")
    private Keyword keyword;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
