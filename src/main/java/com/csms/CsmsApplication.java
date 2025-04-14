package com.csms;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableJpaRepositories("com.csms.repository")
@EntityScan("com.csms.model")
@EnableMethodSecurity
@SpringBootApplication
@SecurityScheme(name = "bearer",type = SecuritySchemeType.HTTP, scheme = "Bearer", in = SecuritySchemeIn.HEADER)
public class CsmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsmsApplication.class, args);
	}

}
