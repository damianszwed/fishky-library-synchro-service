package com.github.damianszwed.fishky.library.synchro.service.adapter.spreadsheets;

import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class GoogleSpreadsheetsService implements SpreadsheetsService {
    private final Sheets service;
    private final String spreadsheetId;
    private final String range;

    public GoogleSpreadsheetsService(Sheets service, String spreadsheetId, String range) {
        this.service = service;
        this.spreadsheetId = spreadsheetId;
        this.range = range;
    }

    @Override
    public Optional<List<List<String>>> getLibraryValues() {
        try {
            final ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            return Optional.ofNullable(response.getValues())
                    .filter(lists -> !lists.isEmpty())
                    .map(lists -> lists.stream()
                            .map(objects -> objects.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.toList()))
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            log.error("An error occurred.", e);
            return Optional.empty();
        }
    }
}
