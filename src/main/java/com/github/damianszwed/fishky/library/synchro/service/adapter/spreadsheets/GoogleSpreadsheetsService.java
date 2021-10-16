package com.github.damianszwed.fishky.library.synchro.service.adapter.spreadsheets;

import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
            //TODO(Damian.Szwed) uporzadkowac, zwrocic wartosc
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            } else {
                System.out.println("Name, Major");
                for (List row : values) {
                    System.out.printf("%s, %s\n", row.get(0), row.get(1));
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
