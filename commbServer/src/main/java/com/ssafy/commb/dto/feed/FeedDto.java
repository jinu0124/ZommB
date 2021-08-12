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
@Alias("Feed")
public class FeedDto {
    private Integer id;
    private Date createAt;
    private String content;
    private Boolean isThumb;
    private Integer thumbCnt;
    private Integer blocked;
    private String feedFileUrl;

    private UserDto user;
    private BookDto book;
    private List<HashTagDto> hashTags;
    private List<CommentDto> comments;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {                // 요청
        private Integer userId;
        private Integer bookId;
        private String keywordId;

        private String content;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {                // 요청
        private Integer bookId;
        private String contents;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {              // 반환
        private FeedDto data;
        private String retMsg;                  // message
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseList {          // 반환
        private List<FeedDto> data;
        private String retMsg;                  // message
    }


}

