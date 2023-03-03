package com.task.socialmedia.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//configuration allows this class create beans
@Configuration
//Enable scheduling allows us perform cron jobs to enable you perform periodic tasks in the scheduler class
@EnableScheduling
public class Scheduler {
    DateTimeFormatter timeStampPattern  = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
    @Scheduled(fixedDelayString = "PT10S", initialDelay = 3000)
    public void fixedDelaySch() {

    }

}

