package io.samanthatobias.bookcatalogue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:git.properties")
//@PropertySource("classpath:auth-${spring.profiles.active}.properties")
public class BookCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCatalogueApplication.class, args);
	}

	@Bean
	public BasicAuthInterceptor basicAuthInterceptor(
			@Value("${basic.auth.username}") String username,
			@Value("${basic.auth.password}") String password) {
		return new BasicAuthInterceptor(username, password);
	}

	@Profile("local")
	@Configuration
	@PropertySource("classpath:auth-local.properties")
	static class Local {
	}

	@Profile("cloud")
	@Configuration
	@PropertySource("classpath:auth-cloud.properties")
	static class Cloud {
	}

}
