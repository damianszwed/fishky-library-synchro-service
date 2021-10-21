package com.github.damianszwed.fishky.library.synchro.service.business;

import com.github.damianszwed.fishky.library.synchro.service.port.LibrarySynchroService;
import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import com.github.damianszwed.fishky.library.synchro.service.port.flashcard.FlashcardFolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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
        final List<FlashcardFolder> spreadsheetFlashcardFolders = FlashcardFoldersMapper.get(spreadsheetsService);
        log.info("Retrieved {} folders.", spreadsheetFlashcardFolders.size());


        final Mono<List<FlashcardFolder>> serverFlashcardFolders = getServerFlashcardFolders();

        serverFlashcardFolders.subscribe(folders -> {
            log.info("Flashcard folders on server: {}.", folders.size());
            removeDanglingFolders(spreadsheetFlashcardFolders, folders);
            removeDanglingFlashcards(spreadsheetFlashcardFolders, folders);
            createFolders(spreadsheetFlashcardFolders).subscribe();
        });

        //TODO(Damian.Szwed) Usun foldery nieistniejace w spreadsheet

        //TODO(Damian.Szwed) Usun fishky w poszczegolnych folderach

        //TODO(Damian.Szwed) Tworz foldery i zapisz wszystkie fishky
        //TODO(Damian.Szwed) Klucz google  oraz application-production.properties ma byc konfigurowalny dla dockera
    }

    private Flux<FlashcardFolder> createFolders(List<FlashcardFolder> spreadsheetFlashcardFolders) {
        return Flux.fromIterable(spreadsheetFlashcardFolders)
                .flatMap(flashcardFolder -> webClient.post()
                        .uri("flashcardFolders")
                        .body(Mono.just(flashcardFolder), FlashcardFolder.class)
                        .exchangeToMono(clientResponse -> {
                            log.info("Creating folder finished with status code: {}.",
                                    clientResponse.statusCode());
                            return Mono.just(flashcardFolder);
                        }));
    }

    private Mono<List<FlashcardFolder>> getServerFlashcardFolders() {
        return webClient.get()
                .uri("flashcardFolders").retrieve().bodyToFlux(FlashcardFolder.class)
                .collectList();
    }

    private void removeDanglingFolders(List<FlashcardFolder> spreadsheetFlashcardFolders, List<FlashcardFolder> folders) {
        final List<String> spreadsheetFolderNames = spreadsheetFlashcardFolders.stream().map(FlashcardFolder::getName).collect(Collectors.toList());
        final List<FlashcardFolder> foldersToRemove = folders.stream().filter(flashcardFolder -> {
            return !spreadsheetFolderNames.contains(flashcardFolder.getName());
        }).collect(Collectors.toList());
        log.info("There is {} folders to remove.", foldersToRemove.size());
        //TODO(Damian.Szwed) remove folders.
    }

    private void removeDanglingFlashcards(List<FlashcardFolder> spreadsheetFlashcardFolders, List<FlashcardFolder> folders) {
        //TODO(Damian.Szwed) implementation
    }

}
