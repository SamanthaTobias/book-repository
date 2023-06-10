package io.samanthatobias.bookcatalogue.config;

import io.samanthatobias.bookcatalogue.BasicAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(name = "auth.enabled", havingValue = "true", matchIfMissing = true)
public class ProdWebConfig implements WebMvcConfigurer {

	private final BasicAuthInterceptor basicAuthInterceptor;

	@Autowired
	Environment environment;

	public ProdWebConfig(@Lazy BasicAuthInterceptor basicAuthInterceptor) {
		this.basicAuthInterceptor = basicAuthInterceptor;
	}

	@Bean
	public BasicAuthInterceptor basicAuthInterceptor() {
		return new BasicAuthInterceptor(environment);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(basicAuthInterceptor).addPathPatterns("/**");
	}

}
