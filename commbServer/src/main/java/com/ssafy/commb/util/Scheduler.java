package com.ssafy.commb.util;

import com.ssafy.commb.service.BookService;
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

    @Scheduled(cron = "1 0 0 * * *")
    public void cronJobDailyUpdate() {

        keywordService.updateKeywordEvent();
    }

    @Scheduled(cron = "1 0 0 1,8,15,22 * *")
    public void cronJobWeeklyUpdate(){

        bookService.updateBookEvent();
    }


}
