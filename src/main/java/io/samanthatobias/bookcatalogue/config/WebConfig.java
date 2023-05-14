package io.samanthatobias.bookcatalogue.config;

import io.samanthatobias.bookcatalogue.BasicAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:auth.properties")
public class WebConfig implements WebMvcConfigurer {

	private final BasicAuthInterceptor basicAuthInterceptor;

	public WebConfig(BasicAuthInterceptor basicAuthInterceptor) {
		this.basicAuthInterceptor = basicAuthInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("Registering BasicAuthInterceptor");
		registry.addInterceptor(basicAuthInterceptor).addPathPatterns("/**");
	}

}
