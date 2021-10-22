package com.github.damianszwed.fishky.library.synchro.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpringTestConfiguration {

    @Bean
    WebClient webClient() {
        return WebClient.create();
    }

}
