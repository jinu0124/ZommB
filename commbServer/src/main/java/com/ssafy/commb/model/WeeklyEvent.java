package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Weekly_Event")
@Getter
@Setter
public class WeeklyEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    @OneToMany(mappedBy = "weeklyEvent")
    private List<temp.WeeklyEventParticipate> weeklyEventParticipates = new ArrayList<temp.WeeklyEventParticipate>();

}
