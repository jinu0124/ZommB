package com.ssafy.commb.util;

import com.ssafy.commb.common.fcm.FcmService;
import com.ssafy.commb.service.BookService;
import com.ssafy.commb.service.ConfirmationTokenService;
import com.ssafy.commb.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Spring Scheduler
 *    *  *  *  *  *  *
 *    초 분 시 일 월 요일
 */
@Component
public class Scheduler {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private FcmService fcmService;

    @Scheduled(cron = "1 0 0 * * *")            // 매일 정각 : 데일리 이벤트 업데이트
    public void cronJobDailyUpdate() {

        keywordService.updateKeywordEvent();
    }

    @Scheduled(cron = "2 0 0 1,8,15,22 * *")    // 1, 8, 15, 22일 정각 : 위클리 이벤트 업데이트
    public void cronJobWeeklyUpdate(){

        bookService.updateBookEvent();
    }

    @Scheduled(cron = "1 10 0 * * *")            // 매일 0시 10분 : 하루전까지 쌓인 confirm Email 토큰 데이터 모두 삭제
    public void cronJobClearConfirmToken(){

        confirmationTokenService.deleteLast();
    }

    @Scheduled(cron = "2 10 0 * * *")           // 매일 0시 10분 : 24시간 이전부터 로그아웃 되지 않고 남아있던 Firebase Registration 데이터 모두 삭제
    public void cronJobClearFirebaseToken(){

        fcmService.deleteLastDay();
    }
}
