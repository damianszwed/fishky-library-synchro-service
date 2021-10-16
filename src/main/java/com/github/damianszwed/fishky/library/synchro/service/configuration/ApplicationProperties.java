package com.github.damianszwed.fishky.library.synchro.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("fishky")
@Data
public class ApplicationProperties {
  private String spreadsheetId = "1lrXBEZqYkWaLu2S7PYFRO7sOShWRktTwMEb2W2DqZZs";
  private String spreadsheetRange = "Library!A2:K";
}
