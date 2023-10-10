package com.raspberry.awards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.raspberry.awards.jpa.model")
@EnableJpaRepositories("com.raspberry.awards.jpa.repository")
public class AwardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwardsApplication.class, args);
	}

}
