package com.ssafy.commb.dto.feed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.dto.user.UserDto;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Getter                                     // Getter 생성
@Setter                                     // Setter 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 JSON 반환 시 제외
@AllArgsConstructor                         // Builder pattern 사용 시 반드시 전체 인자를 포함하는 생성자 필수
@NoArgsConstructor                          // 기본 생성자
@Builder                                    // Builder 패턴 사용
@Alias("Comment")
public class CommentDto {
    private Integer id;
    private String content;
    private Integer userId;
    private String nickname;
    private Integer thumbCnt;
    private Date createAt;
    private Boolean isThumb;
    private Boolean isMod;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {                // 요청
        private String feedId;
        private String userId;

        private String comment;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {               // 반환
        private CommentDto data;
        private String retMsg;                  // message
    }

}

