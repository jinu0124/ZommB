package com.ssafy.commb.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiImplicitParam;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter                                     // Getter 생성
@Setter                                     // Setter 생성
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 JSON 반환 시 제외
@AllArgsConstructor                         // Builder pattern 사용 시 반드시 전체 인자를 포함하는 생성자 필수
@NoArgsConstructor                          // 기본 생성자
@Builder                                    // Builder 패턴 사용
@Alias("MyEvent")
public class MyEventDto {
    private Integer id;
    private Integer bookmark;
    private Integer dailyParticipate;
    private Boolean pencilOn;
    private Boolean bookmarkOn;

    private List<WeeklyEventDto> weekly;

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
        private MyEventDto data;
        private String retMsg;
    }

}