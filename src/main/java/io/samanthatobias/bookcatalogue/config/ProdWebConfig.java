package io.samanthatobias.bookcatalogue.config;

import io.samanthatobias.bookcatalogue.BasicAuthInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(name = "auth.enabled", havingValue = "true", matchIfMissing = true)
public class ProdWebConfig implements WebMvcConfigurer {

	private final BasicAuthInterceptor basicAuthInterceptor;

	public ProdWebConfig(@Lazy BasicAuthInterceptor basicAuthInterceptor) {
		this.basicAuthInterceptor = basicAuthInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(basicAuthInterceptor).addPathPatterns("/**");
	}

}
