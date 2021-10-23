package com.github.damianszwed.fishky.library.synchro.service.adapter.spreadsheets;

import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class GoogleSpreadsheetServiceFactory {
    private static final String APPLICATION_NAME = "Fishky Library Synchro Service";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    private static Optional<Credential> getCredential() {
        return getCredentialsStream()
                .map(GoogleSpreadsheetServiceFactory::getCredentialFromStream)
                .orElseGet(() -> {
                    log.error("Resource not found: {}", CREDENTIALS_FILE_PATH);
                    return Optional.empty();
                });
    }

    private static Optional<InputStream> getCredentialsStream() {
        try {
            return Optional.of(new FileInputStream(CREDENTIALS_FILE_PATH));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    private static Optional<Credential> getCredentialFromStream(InputStream inputStream) {
        try {
            return Optional.of(GoogleCredential.fromStream(inputStream, new NetHttpTransport(), JSON_FACTORY)
                    .createScoped(SCOPES));
        } catch (IOException e) {
            log.error("An error occurred.", e);
            return Optional.empty();
        }
    }

    public static Optional<SpreadsheetsService> provide(String spreadsheetId, String spreadsheetRange) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return getCredential()
                .map(credential -> {
                    final Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                            .setApplicationName(APPLICATION_NAME)
                            .build();
                    return new GoogleSpreadsheetsService(service, spreadsheetId, spreadsheetRange);
                });
    }
}
