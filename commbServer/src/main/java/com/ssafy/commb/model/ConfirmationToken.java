package com.ssafy.commb.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmationToken implements Serializable {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;   // 토큰 만료 시간

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @Column
    private LocalDateTime expirationDate;

    @Column
    private boolean expired;

    @Column
    private int userId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    // 일대일 관계
    @OneToOne
    @JoinColumn(name = "userId", insertable=false, updatable=false)
    private User user;

    // 이메일 인증 토큰 생성
    public static ConfirmationToken createEmailConfirmationToken(int userId){
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.expirationDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE); // 5분후 만료
        confirmationToken.userId = userId;
        confirmationToken.createDate = LocalDateTime.now();
        confirmationToken.lastModifiedDate = LocalDateTime.now();
        confirmationToken.expired = false;
        return confirmationToken;
    }

    // 토큰 사용으로 인한 만료
    public void useToken(){
        expired = true;
    }

}
