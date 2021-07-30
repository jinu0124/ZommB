package com.ssafy.commb.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WeeklyEventParticipateId implements Serializable {
    @Column(name="weekly_id")
    private int weeklyId;

    @Column(name="user_id")
    private int userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeeklyEventParticipateId)) return false;
        WeeklyEventParticipateId weeklyEventParticipateId = (WeeklyEventParticipateId) o;
        return weeklyId == weeklyEventParticipateId.weeklyId && userId == weeklyEventParticipateId.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weeklyId, userId);
    }
}
