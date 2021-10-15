package com.github.damianszwed.fishky.library.synchro.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class ScheduledService {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRateString = "${fishky.scheduledRate}")
    public void execute() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
