package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Weekly_Event_Participate")
@Getter
@Setter
public class WeeklyEventParticipate {
    @EmbeddedId
    private WeeklyEventParticipateId id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name="weekly_id", insertable=false, updatable=false)
    private WeeklyEvent weeklyEvent;

    @ManyToOne
    @JoinColumn(name="user_id", insertable=false, updatable=false)
    private User user;

}
