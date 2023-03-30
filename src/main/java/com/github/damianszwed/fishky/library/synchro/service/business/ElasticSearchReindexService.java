package com.github.damianszwed.fishky.library.synchro.service.business;

import com.github.damianszwed.fishky.library.synchro.service.port.ReindexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class ElasticSearchReindexService implements ReindexService {
    private final WebClient webClient;

    public ElasticSearchReindexService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void reindex() {
        webClient.post()
                .uri("flashcardFolders/reindex")
                .retrieve()
                .toBodilessEntity()
                .subscribe(voidResponseEntity -> log.info("Elastic search reindex done."));
    }
}
