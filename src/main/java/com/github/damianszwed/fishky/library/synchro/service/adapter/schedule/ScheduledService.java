package com.github.damianszwed.fishky.library.synchro.service.adapter.schedule;

import com.github.damianszwed.fishky.library.synchro.service.port.LibrarySynchroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScheduledService {

    private final LibrarySynchroService librarySynchroService;

    public ScheduledService(LibrarySynchroService librarySynchroService) {
        this.librarySynchroService = librarySynchroService;
    }

    @Scheduled(fixedRateString = "${fishky.scheduled-rate}")
    public void execute() {
        log.info("Executing librarySynchroService.synchronize().");
        try {
            librarySynchroService.synchronize();
        } catch (Exception e) {
            log.error("An error occurred on librarySynchroService.synchronize().", e);
        }
        log.info("End of the librarySynchroService.synchronize().");
    }
}
