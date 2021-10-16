package com.github.damianszwed.fishky.library.synchro.service.adapter.spreadsheets;

import com.github.damianszwed.fishky.library.synchro.service.configuration.ApplicationProperties;
import com.github.damianszwed.fishky.library.synchro.service.port.SpreadsheetsService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * TODO(Damian.Szwed) uporzadkowac
 */
public class GoogleSpreadsheetServiceFactory {
    private static final String APPLICATION_NAME = "Fishky Library Synchro Service";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials() throws IOException {
        InputStream in = GoogleSpreadsheetServiceFactory.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        return getCredentials(in);
    }

    private static Credential getCredentials(final InputStream inputStream) throws
            IOException {

        return GoogleCredential.fromStream(inputStream, new NetHttpTransport(), JSON_FACTORY)
                .createScoped(SCOPES);
    }

    public static SpreadsheetsService provide(ApplicationProperties applicationProperties) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
        return new GoogleSpreadsheetsService(service, applicationProperties.getSpreadsheetId(), applicationProperties.getSpreadsheetRange());
    }
}
