package io.samanthatobias.bookcatalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("classpath:git.properties")
@EnableConfigurationProperties
public class BookCatalogueApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(BookCatalogueApplication.class, args);
	}

	@Profile("local")
	@Configuration
	@PropertySource("classpath:auth-local.properties")
	static class Local {
		static {
			System.out.println("AUTH PROPERTIES: LOCAL");
		}
	}

	@Profile("cloud")
	@Configuration
	@PropertySource("classpath:auth-cloud.properties")
	static class Cloud {
		static {
			System.out.println("AUTH PROPERTIES: CLOUD");
		}
	}


}
