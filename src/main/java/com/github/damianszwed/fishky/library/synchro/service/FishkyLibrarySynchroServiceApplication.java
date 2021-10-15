package com.github.damianszwed.fishky.library.synchro.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FishkyLibrarySynchroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FishkyLibrarySynchroServiceApplication.class, args);
	}

}
