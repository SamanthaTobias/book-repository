package io.samanthatobias.bookcatalogue.config;

import io.samanthatobias.bookcatalogue.BasicAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicAuthConfig {

	@Autowired
	private BasicAuthProperties basicAuthProperties;

	@Bean
	public BasicAuthInterceptor basicAuthInterceptor() {
		return new BasicAuthInterceptor(
				basicAuthProperties.getUsername(),
				basicAuthProperties.getPassword()
		);
	}

}
