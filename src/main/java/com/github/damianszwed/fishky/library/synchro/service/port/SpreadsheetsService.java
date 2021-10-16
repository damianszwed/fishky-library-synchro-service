package com.github.damianszwed.fishky.library.synchro.service.port;

import java.util.List;
import java.util.Optional;

public interface SpreadsheetsService {
    Optional<List<List<String>>> getLibraryValues();
}
