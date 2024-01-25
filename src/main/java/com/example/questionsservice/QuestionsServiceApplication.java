package com.example.questionsservice;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuestionsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionsServiceApplication.class, args);
	}

	@Bean
	public OpenAPI questionsServiceOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("question service API")
						.description("question service for quiz application")
						.version("v1.0.0")
						.license(new License().name("Apache 2.0").url("")))
				.externalDocs(new ExternalDocumentation()
						.description("SpringDocs Wiki Documentation")
						.url("https://springdoc.org/"));
	}

}
