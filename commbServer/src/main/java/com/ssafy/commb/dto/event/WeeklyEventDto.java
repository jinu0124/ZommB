package com.ssafy.commb.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.commb.dto.book.BookDto;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Component
@Getter                                     // Getter 생성
@Setter                                     // Setter 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 JSON 반환 시 제외
@AllArgsConstructor                         // Builder pattern 사용 시 반드시 전체 인자를 포함하는 생성자 필수
@NoArgsConstructor                          // 기본 생성자
@Builder                                    // Builder 패턴 사용
@Alias("WeeklyEvent")
public class WeeklyEventDto {
//    private Weekly weekly;
    private Integer weeklyId;
//    private BookDto book;
    private Integer bookId;

    private String bookFileUrl;
    private Boolean weeklyParticipate;
    private Integer week;

//    @Setter
//    @Getter
//    public static class Weekly{
//        private Integer id;
//    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{                // 요청
        private Integer today;
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
