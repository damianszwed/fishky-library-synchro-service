package com.github.damianszwed.fishky.library.synchro.service.configuration;

import com.github.damianszwed.fishky.library.synchro.service.adapter.schedule.ScheduledElasticSearchIndexService;
import com.github.damianszwed.fishky.library.synchro.service.business.ElasticSearchReindexService;
import com.github.damianszwed.fishky.library.synchro.service.port.ReindexService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile("elasticsearch")
public class ElasticSearchSynchroConfiguration {

    @Bean
    ScheduledElasticSearchIndexService scheduledElasticSearchIndexService(ReindexService reindexService) {
        return new ScheduledElasticSearchIndexService(reindexService);
    }

    @Bean
    ReindexService reindexService(WebClient webClient) {
        return new ElasticSearchReindexService(webClient);
    }

}
