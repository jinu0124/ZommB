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
    private temp.WeeklyEventParticipateId id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name="weekly_id")
    private WeeklyEvent weeklyEvent;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
