package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
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
    @JoinColumn(name="daily_id", insertable=false, updatable=false)
    private DailyEvent dailyEvent;

    @ManyToOne
    @JoinColumn(name="user_id", insertable=false, updatable=false)
    private User user;
}
