package io.samanthatobias.bookcatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCatalogueApplication.class, args);
	}

	@Bean
	public BasicAuthInterceptor basicAuthInterceptor() {
		return new BasicAuthInterceptor("admin", "password");
	}

}
