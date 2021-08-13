package com.ssafy.commb.dto.fcm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.commb.dto.event.MyEventDto;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmDto {
    private boolean validate_only;
    private Message message;

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Message{
        private Notification notification;
        private String token;
        private PayData data;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Notification{
        private String title;
        private String body;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class PayData{
        private Integer userId;
        private String nickname;
        private String userFileUrl;
        private Integer feedId;
        private String feedFileUrl;
        private Integer commentId;
        private String comment;
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
