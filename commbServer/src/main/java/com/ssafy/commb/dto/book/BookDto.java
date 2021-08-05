package com.ssafy.commb.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter                                     // Getter 생성
@Setter                                     // Setter 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 JSON 반환 시 제외
@AllArgsConstructor                         // Builder pattern 사용 시 반드시 전체 인자를 포함하는 생성자 필수
@NoArgsConstructor                          // 기본 생성자
@Builder                                    // Builder 패턴 사용
public class BookDto {
    private Integer id;
    private String bookName;
    private String author;
    private String publisher;
    private Integer year;
    private String genre;
    private String isbn;
    private String bookFileUrl;
    private Integer readCnt;

    private String contents;

    private Boolean isRead;
    private Float rate;

    private List<KeywordDto> keyword;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookSearchRequest{                // 요청
        private String searchType;
        private String searchWord;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest{                // 요청
        private Integer id;
        private boolean isRead;
        private float rate;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookShelfSearchRequest{                // 요청
        private String bookName;
        private int userId;
        private boolean isRead;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopBarRegisterRequest {                // 요청
        private Integer id;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{               // 반환
        private BookDto data;
        private String retMsg;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseList{               // 반환
        private List<BookDto> data;
        private String retMsg;
    }
}
