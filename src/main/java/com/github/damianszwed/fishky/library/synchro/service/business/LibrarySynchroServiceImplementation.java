package com.github.damianszwed.fishky.library.synchro.service.business;

import com.github.damianszwed.fishky.library.synchro.service.port.LibrarySynchroService;
import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class LibrarySynchroServiceImplementation implements LibrarySynchroService {
    private final SpreadsheetsService spreadsheetsService;

    public LibrarySynchroServiceImplementation(SpreadsheetsService spreadsheetsService) {
        this.spreadsheetsService = spreadsheetsService;
    }

    @Override
    public void synchronize() {
        final Optional<List<List<String>>> libraryValues = spreadsheetsService.getLibraryValues();
        libraryValues.ifPresent(lists -> lists.forEach(folder -> {
            log.info("Folder {}", folder.get(0));
            log.info("Question {}", folder.get(1));
        }));
        //TODO(Damian.Szwed) Czytaj kolumny z Fishky Library
        //TODO(Damian.Szwed) Klucz ma byc konfigurowalny
        //TODO(Damian.Szwed) Mapuj do konkretnych DTO
        //TODO(Damian.Szwed) wykonaj request non-sec do flashcard service
        //TODO(Damian.Szwed) wykonaj request sec do flashcard service
        //TODO(Damian.Szwed) Pobierz wszystkie library foldery
        //TODO(Damian.Szwed) Usun foldery nieistniejace w spreadsheet
        //TODO(Damian.Szwed) Usun fishky w poszczegolnych folderach
    }
}
