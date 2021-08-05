package com.ssafy.commb.dto.bookshelf;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Component
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Alias("BookShelfCnt")
public class BookShelfCntDto {
    private int libraryCnt;
    private int bookcartCnt;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private BookShelfCntDto data;
        private String retMsg;
    }
}
