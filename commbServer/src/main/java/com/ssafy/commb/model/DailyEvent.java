package com.ssafy.commb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Daily_Event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name="keyword_id")
    private Keyword keyword;

    @OneToMany(mappedBy = "dailyEvent")
    private List<DailyEventParticipate> dailyEventParticipates = new ArrayList<DailyEventParticipate>();
}
