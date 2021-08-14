package com.ssafy.commb.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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

    @CreatedDate
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
