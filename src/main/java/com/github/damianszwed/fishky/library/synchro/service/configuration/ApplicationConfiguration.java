package com.github.damianszwed.fishky.library.synchro.service.configuration;

import com.github.damianszwed.fishky.library.synchro.service.adapter.schedule.ScheduledService;
import com.github.damianszwed.fishky.library.synchro.service.adapter.spreadsheets.GoogleSpreadsheetServiceFactory;
import com.github.damianszwed.fishky.library.synchro.service.business.LibrarySynchroServiceImplementation;
import com.github.damianszwed.fishky.library.synchro.service.port.LibrarySynchroService;
import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    @Bean
    public SpreadsheetsService spreadsheetsService(ApplicationProperties applicationProperties) throws GeneralSecurityException, IOException {
        return GoogleSpreadsheetServiceFactory.provide(applicationProperties);
    }

    @Bean
    public LibrarySynchroService librarySynchroService(SpreadsheetsService spreadsheetsService) {
        return new LibrarySynchroServiceImplementation(spreadsheetsService);
    }

    @Bean
    public ScheduledService scheduledService(LibrarySynchroService librarySynchroService) {
        return new ScheduledService(librarySynchroService);
    }
}