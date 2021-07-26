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
    private DailyEventParticipateId id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name="daily_id")
    private DailyEvent dailyEvent;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
