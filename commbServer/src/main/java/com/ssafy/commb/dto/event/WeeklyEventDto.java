package com.ssafy.commb.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.commb.dto.book.BookDto;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter                                     // Getter 생성
@Setter                                     // Setter 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 JSON 반환 시 제외
@AllArgsConstructor                         // Builder pattern 사용 시 반드시 전체 인자를 포함하는 생성자 필수
@NoArgsConstructor                          // 기본 생성자
@Builder                                    // Builder 패턴 사용
public class WeeklyEventDto {
    private Weekly weekly;
    private BookDto book;

    private String bookFileUrl;
    private boolean weeklyParticipate;

    static class Weekly{
        int id;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{                // 요청
        private int today;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{               // 반환
        private WeeklyEventDto data;
        private String retMsg;
    }

}
