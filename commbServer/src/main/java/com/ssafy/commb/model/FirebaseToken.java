package com.ssafy.commb.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "firebase_token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
