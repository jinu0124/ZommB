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
    @JoinColumn(name="keyword_id", insertable=false, updatable=false)
    private Keyword keyword;

    @ManyToOne
    @JoinColumn(name="user_id", insertable=false, updatable=false)
    private User user;
}
