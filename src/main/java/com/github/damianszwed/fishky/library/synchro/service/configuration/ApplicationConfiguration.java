package com.github.damianszwed.fishky.library.synchro.service.configuration;

import com.github.damianszwed.fishky.library.synchro.service.adapter.schedule.ScheduledLibrarySynchroServiceService;
import com.github.damianszwed.fishky.library.synchro.service.adapter.spreadsheets.GoogleSpreadsheetServiceFactory;
import com.github.damianszwed.fishky.library.synchro.service.business.LibrarySynchroServiceImplementation;
import com.github.damianszwed.fishky.library.synchro.service.port.LibrarySynchroService;
import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public SpreadsheetsService spreadsheetsService(
            @Value("${fishky.spreadsheet-id}") String spreadsheetId,
            @Value("${fishky.spreadsheet-range}") String spreadsheetRange
    ) throws GeneralSecurityException, IOException {
        return GoogleSpreadsheetServiceFactory.provide(spreadsheetId, spreadsheetRange)
                .orElseThrow(() -> new BeanCreationException("Cannot create SpreadsheetsService. Check the errors above."));
    }

    @Bean
    public LibrarySynchroService librarySynchroService(SpreadsheetsService spreadsheetsService, WebClient webClient) {
        return new LibrarySynchroServiceImplementation(spreadsheetsService, webClient);
    }

    @Bean
    public ScheduledLibrarySynchroServiceService scheduledService(LibrarySynchroService librarySynchroService) {
        return new ScheduledLibrarySynchroServiceService(librarySynchroService);
    }
}
