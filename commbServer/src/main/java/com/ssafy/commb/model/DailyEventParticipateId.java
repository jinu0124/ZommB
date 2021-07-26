package com.ssafy.commb.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DailyEventParticipateId {

    @Column(name="daily_id")
    private int dailyId;

    @Column(name="user_id")
    private int userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyEventParticipateId)) return false;
        DailyEventParticipateId that = (DailyEventParticipateId) o;
        return dailyId == that.dailyId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dailyId, userId);
    }
}
