package com.ssafy.commb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReportId implements Serializable {
    @Column(name = "feed_id")
    private int feedId;

    @Column(name = "user_id")
    private int userId;

    public ReportId(){}
    public ReportId(int feedId, int userId) {
        this.feedId = feedId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportId)) return false;
        ReportId reportId = (ReportId) o;
        return feedId == reportId.feedId && userId == reportId.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedId, userId);
    }
}
