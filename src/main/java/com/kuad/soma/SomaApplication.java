package com.kuad.soma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SomaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SomaApplication.class, args);
	}

}
