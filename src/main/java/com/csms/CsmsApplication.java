package com.csms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.csms.repository")
@EntityScan("com.csms.model")
@SpringBootApplication
public class CsmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsmsApplication.class, args);
	}

}
