package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Oauth_Provider")
@Getter
@Setter
public class OauthProvider {
    @Id
    private int id;

    private String providerName;

    // User와 1대1 맵핑
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
