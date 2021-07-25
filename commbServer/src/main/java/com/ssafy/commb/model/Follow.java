package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Follow")
@Getter
@Setter
public class Follow {

    @Id
    @ManyToOne
    @JoinColumn(name="follower")
    private User follower;

    @Id
    @ManyToOne
    @JoinColumn(name="following")
    private User following;
}
