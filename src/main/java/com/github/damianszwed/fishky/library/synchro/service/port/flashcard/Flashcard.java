package com.github.damianszwed.fishky.library.synchro.service.port.flashcard;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Flashcard {

  String id;

  String question;
  @Builder.Default
  List<String> answers = new ArrayList<>();
}