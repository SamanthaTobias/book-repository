package io.samanthatobias.bookcatalogue.config;

import io.samanthatobias.bookcatalogue.BasicAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("test")
public class TestWebConfig implements WebMvcConfigurer {

	private final BasicAuthInterceptor basicAuthInterceptor;

	public TestWebConfig(BasicAuthInterceptor basicAuthInterceptor) {
		this.basicAuthInterceptor = basicAuthInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(basicAuthInterceptor).addPathPatterns("/**");
	}

}
