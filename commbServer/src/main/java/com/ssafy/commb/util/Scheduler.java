package com.ssafy.commb.util;

import com.ssafy.commb.service.BookService;
import com.ssafy.commb.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private BookService bookService;

    // 초 분 시 일 월 요일

    @Scheduled(cron = "1 0 0 * * *")
    public void cronJobDailyUpdate() {
        System.out.println("Daily 스케줄러");

        keywordService.updateKeywordEvent();        // Query 로 로직 수행
    }

    @Scheduled(cron = "1 0 0 1,8,15,22 * *")
    public void cronJobWeeklyUpdate(){
        System.out.println("Weekly 스케줄러");

        bookService.updateBookEvent();              // Query로 뽑아서 Application, JPA로 로직 수행
    }


}
