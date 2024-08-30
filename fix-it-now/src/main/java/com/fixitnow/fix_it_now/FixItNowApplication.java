package com.fixitnow.fix_it_now;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
@EnableJpaRepositories("com.fixitnow.fix_it_now.repository")
public class FixItNowApplication {

	public static void main(String[] args) {
		SpringApplication.run(FixItNowApplication.class, args);
	}

}
