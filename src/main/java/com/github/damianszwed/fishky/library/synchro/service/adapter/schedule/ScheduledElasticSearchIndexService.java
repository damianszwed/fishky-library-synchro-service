package com.github.damianszwed.fishky.library.synchro.service.adapter.schedule;

import com.github.damianszwed.fishky.library.synchro.service.port.ReindexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScheduledElasticSearchIndexService {

    private final ReindexService reindexService;

    public ScheduledElasticSearchIndexService(ReindexService reindexService) {
        this.reindexService = reindexService;
    }

    @Scheduled(fixedRateString = "${fishky.scheduled-library-synchro-rate}", initialDelay = 60000)
    public void execute() {
        log.info("Executing elasticSearchReindexService.reindex().");
        try {
            reindexService.reindex();
        } catch (Exception e) {
            log.error("An error occurred on elasticSearchReindexService.reindex().", e);
        }
        log.info("End of the elasticSearchReindexService.reindex().");
    }
}
