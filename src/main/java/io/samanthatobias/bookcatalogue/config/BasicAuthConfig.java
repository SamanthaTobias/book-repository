package io.samanthatobias.bookcatalogue.config;

import io.samanthatobias.bookcatalogue.BasicAuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class BasicAuthConfig {

    @Value("${basic.auth.username}")
    private String username;

    @Value("${basic.auth.password}")
    private String password;

    @Bean
    @DependsOn({"localConfig", "cloudConfig"})
    public BasicAuthInterceptor basicAuthInterceptor() {
        return new BasicAuthInterceptor(username, password);
    }

}
