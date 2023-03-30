package com.github.damianszwed.fishky.library.synchro.service.adapter.schedule;

import com.github.damianszwed.fishky.library.synchro.service.port.LibrarySynchroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScheduledLibrarySynchroService {

    private final LibrarySynchroService librarySynchroService;

    public ScheduledLibrarySynchroService(LibrarySynchroService librarySynchroService) {
        this.librarySynchroService = librarySynchroService;
    }

    @Scheduled(fixedRateString = "${fishky.scheduled-elasticsearch-reindex-rate}", initialDelay = 10000)
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
