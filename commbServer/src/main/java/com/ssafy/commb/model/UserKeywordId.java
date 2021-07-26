package com.ssafy.commb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserKeywordId implements Serializable {
    @Column(name = "keyword_id")
    private int keywordId;

    @Column(name = "user_id")
    private int userId;

    UserKeywordId() {}

    public UserKeywordId(int keywordId, int userId) {
        this.keywordId = keywordId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserKeywordId)) return false;
        UserKeywordId that = (UserKeywordId) o;
        return keywordId == that.keywordId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywordId, userId);
    }
}
