package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Follow")
@Getter
@Setter
public class Follow implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="follower")
    private temp.User follower;

    @Id
    @ManyToOne
    @JoinColumn(name="following")
    private temp.User following;
}
