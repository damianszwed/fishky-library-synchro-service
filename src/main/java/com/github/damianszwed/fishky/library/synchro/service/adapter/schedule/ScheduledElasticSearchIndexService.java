package com.github.damianszwed.fishky.library.synchro.service.adapter.schedule;

import com.github.damianszwed.fishky.library.synchro.service.port.ElasticSearchReindexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScheduledElasticSearchIndexService {

    private final ElasticSearchReindexService elasticSearchReindexService;

    public ScheduledElasticSearchIndexService(ElasticSearchReindexService elasticSearchReindexService) {
        this.elasticSearchReindexService = elasticSearchReindexService;
    }

    @Scheduled(fixedRateString = "${fishky.scheduled-library-synchro-rate}", initialDelay = 30000)
//    @Scheduled(fixedRateString = "${fishky.scheduled-library-synchro-rate}")//TODO(Damian.Szwed) uncomment this line and remove above one.
    public void execute() {
        log.info("Executing elasticSearchReindexService.reindex().");
        try {
            elasticSearchReindexService.reindex();
        } catch (Exception e) {
            log.error("An error occurred on elasticSearchReindexService.reindex().", e);
        }
        log.info("End of the elasticSearchReindexService.reindex().");
    }
}
