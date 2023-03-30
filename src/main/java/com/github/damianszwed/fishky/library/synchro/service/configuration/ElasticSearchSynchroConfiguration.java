package com.github.damianszwed.fishky.library.synchro.service.configuration;

import com.github.damianszwed.fishky.library.synchro.service.adapter.schedule.ScheduledElasticSearchIndexService;
import com.github.damianszwed.fishky.library.synchro.service.port.ElasticSearchReindexService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("elasticsearch")
public class ElasticSearchSynchroConfiguration {

    @Bean
    ScheduledElasticSearchIndexService scheduledElasticSearchIndexService(ElasticSearchReindexService elasticSearchReindexService) {
        return new ScheduledElasticSearchIndexService(elasticSearchReindexService);
    }

    @Bean
    ElasticSearchReindexService elasticSearchReindexService() {
        return new ElasticSearchReindexService() {
            @Override
            public void reindex() {
                System.out.println("....");
            }
        };
    }

}
