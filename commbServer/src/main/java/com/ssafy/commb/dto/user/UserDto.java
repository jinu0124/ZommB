package com.ssafy.commb.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.commb.dto.user.follow.FollowDto;
import com.ssafy.commb.dto.user.level.LevelDto;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.List;

@Component
@Getter                                     // Getter 생성
@Setter                                     // Setter 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 JSON 반환 시 제외
@AllArgsConstructor                         // Builder pattern 사용 시 반드시 전체 인자를 포함하는 생성자 필수
@NoArgsConstructor                          // 기본 생성자
@Builder                                    // Builder 패턴 사용
@Alias("User")
public class UserDto {
    private Integer id;
    private String email;
    private String name;
    private String nickname;
    private String role;

    @Nullable
    private String userFileUrl;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String oldPassword;
    @JsonIgnore
    private String newPassword;

    private LevelDto level;
    private FollowDto follow;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{                // 요청
        private String email;
        private String password;
        private String name;
        private String nickname;
        private String role;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyPwRequest{                // 요청
        private String oldPassword;

        @Column(name = "password")
        private String newPassword;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{               // 반환
        private UserDto data;
        private String retMsg;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseList{               // 반환
        private List<UserDto> data;
        private String retMsg;
    }

}
