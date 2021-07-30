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
    @JoinColumn(name="feed_id", insertable=false, updatable=false )
    private Feed feed;

    @ManyToOne
    @JoinColumn(name="user_id", insertable=false, updatable=false)
    private User user;
}
