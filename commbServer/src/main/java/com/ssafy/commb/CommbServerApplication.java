package com.ssafy.commb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommbServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(CommbServerApplication.class, args);
    }

}
