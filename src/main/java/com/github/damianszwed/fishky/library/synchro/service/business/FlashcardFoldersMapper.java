package com.github.damianszwed.fishky.library.synchro.service.business;

import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import com.github.damianszwed.fishky.library.synchro.service.port.flashcard.Flashcard;
import com.github.damianszwed.fishky.library.synchro.service.port.flashcard.FlashcardFolder;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
public class FlashcardFoldersMapper {
    public static List<FlashcardFolder> get(SpreadsheetsService spreadsheetsService) {
        return spreadsheetsService.getLibraryValues()
                .map(spreadsheetLines -> {
                    log.info("Found {} lines.", spreadsheetLines.size());
                    return spreadsheetLines;
                })
                .map(FlashcardFoldersMapper::toFolders)
                .orElse(Collections.emptyList());
    }

    private static List<FlashcardFolder> toFolders(List<List<String>> spreadsheetLines) {
        return spreadsheetLines
                .stream()
                .map(FlashcardFoldersMapper::toTupleByFolderName)
                .collect(groupingBy(Tuple2::getT1))
                .entrySet()
                .stream()
                .map(FlashcardFoldersMapper::toFolder)
                .collect(Collectors.toList());
    }

    private static Tuple2<String, Flashcard> toTupleByFolderName(List<String> spreadsheetLine) {
        final String folderName = spreadsheetLine.get(0);
        final String question = spreadsheetLine.get(1);
        final List<String> answers = spreadsheetLine.subList(2, spreadsheetLine.size());
        return Tuples.of(folderName, Flashcard.builder()
                .question(question)
                .answers(answers)
                .build());
    }

    private static FlashcardFolder toFolder(Map.Entry<String, List<Tuple2<String, Flashcard>>> entry) {
        return FlashcardFolder.builder()
                .name(entry.getKey())
                .flashcards(toFlashcards(entry))
                .build();
    }

    private static List<Flashcard> toFlashcards(Map.Entry<String, List<Tuple2<String, Flashcard>>> entry) {
        return entry.getValue().stream().map(Tuple2::getT2)
                .collect(Collectors.toList());
    }
}
