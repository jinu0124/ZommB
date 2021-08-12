package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Daily_Event_Participate")
@Getter
@Setter
public class DailyEventParticipate {
    @EmbeddedId
    private DailyEventParticipateId id = new DailyEventParticipateId(); // null 값 방지

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @MapsId("dailyId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="daily_id", insertable = false, updatable = false)
    private DailyEvent dailyEvent;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User user;
}
