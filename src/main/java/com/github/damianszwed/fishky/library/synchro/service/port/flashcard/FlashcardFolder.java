package com.github.damianszwed.fishky.library.synchro.service.port.flashcard;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class FlashcardFolder {

    String id;

    String name;

    String owner;

    @Builder.Default
    List<Flashcard> flashcards = new ArrayList<>();
}