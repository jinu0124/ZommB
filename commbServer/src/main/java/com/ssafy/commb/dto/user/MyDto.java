package com.ssafy.commb.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Component
@Getter                                     // Getter 생성
@Setter                                     // Setter 생성
@AllArgsConstructor                         // Builder pattern 사용 시 반드시 전체 인자를 포함하는 생성자 필수
@NoArgsConstructor                          // 기본 생성자
@Builder                                    // Builder 패턴 사용
public class MyDto {
    private Integer id;
    private String nickname;
    private String userFileUrl;
    private Boolean isFollow;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {                // 요청
        @Email
        @NotNull
        private String email;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
        private String password;
        
        @NotNull
        private String name;

        @Length(min = 1, max = 10)
        private String nickname;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {                // 요청
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenRequest {                // 요청
        private Integer id;
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyRequest {                // 요청
        private String nickname;
        private Integer flag;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {               // 반환
        private MyDto data;
        private String retMsg;
    }

}
