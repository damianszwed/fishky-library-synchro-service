package com.github.damianszwed.fishky.library.synchro.service.business;

import com.github.damianszwed.fishky.library.synchro.service.port.LibrarySynchroService;
import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import com.github.damianszwed.fishky.library.synchro.service.port.flashcard.FlashcardFolder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LibrarySynchroServiceImplementation implements LibrarySynchroService {
    private final SpreadsheetsService spreadsheetsService;

    public LibrarySynchroServiceImplementation(SpreadsheetsService spreadsheetsService) {
        this.spreadsheetsService = spreadsheetsService;
    }

    @Override
    public void synchronize() {
        final List<FlashcardFolder> flashcardFolders = FlashcardFoldersMapper.get(spreadsheetsService);
        log.info("Retrieved {} folders.", flashcardFolders.size());


        //TODO(Damian.Szwed) wykonaj request non-sec do flashcard service
        //TODO(Damian.Szwed) wykonaj request sec do flashcard service
        //TODO(Damian.Szwed) Pobierz wszystkie library foldery
        //TODO(Damian.Szwed) Usun foldery nieistniejace w spreadsheet
        //TODO(Damian.Szwed) Usun fishky w poszczegolnych folderach
        //TODO(Damian.Szwed) Klucz google ma byc konfigurowalny dla dockera
    }

}
