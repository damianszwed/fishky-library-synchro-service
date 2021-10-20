package com.github.damianszwed.fishky.library.synchro.service.business;

import com.github.damianszwed.fishky.library.synchro.service.port.LibrarySynchroService;
import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import com.github.damianszwed.fishky.library.synchro.service.port.flashcard.FlashcardFolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class LibrarySynchroServiceImplementation implements LibrarySynchroService {
    private final SpreadsheetsService spreadsheetsService;
    private final WebClient webClient;

    public LibrarySynchroServiceImplementation(SpreadsheetsService spreadsheetsService, WebClient webClient) {
        this.spreadsheetsService = spreadsheetsService;
        this.webClient = webClient;
    }

    @Override
    public void synchronize() {
        final List<FlashcardFolder> flashcardFolders = FlashcardFoldersMapper.get(spreadsheetsService);
        log.info("Retrieved {} folders.", flashcardFolders.size());

        webClient.get()
                .uri("http://localhost:8080/flashcardFolders")//TODO(Damian.Szwed) flashcard service URI configurable
                .exchangeToMono(clientResponse -> {
                    log.info("clientResponse.headers() =  {}", clientResponse.headers());
                    log.info("clientResponse.statusCode() = {}", clientResponse.statusCode());
                    return Mono.just(clientResponse);
                })
                .subscribe();

        //TODO(Damian.Szwed) Pobierz wszystkie library foldery
        //TODO(Damian.Szwed) Usun foldery nieistniejace w spreadsheet
        //TODO(Damian.Szwed) Usun fishky w poszczegolnych folderach
        //TODO(Damian.Szwed) Klucz google  oraz application-production.properties ma byc konfigurowalny dla dockera
    }

}
