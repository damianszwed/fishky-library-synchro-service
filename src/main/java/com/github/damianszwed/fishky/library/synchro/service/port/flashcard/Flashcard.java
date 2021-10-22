package com.github.damianszwed.fishky.library.synchro.service.port.flashcard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Flashcard {

    String id;

    String question;
    @Builder.Default
    List<String> answers = new ArrayList<>();
}