package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Report")
@Getter
@Setter
public class Report {
    @EmbeddedId
    private ReportId id;

    private String reason;

    @ManyToOne
    @JoinColumn(name="feed_id")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
