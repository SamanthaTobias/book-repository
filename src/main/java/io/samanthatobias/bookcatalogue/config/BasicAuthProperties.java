package io.samanthatobias.bookcatalogue.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "basic.auth")
public class BasicAuthProperties {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

}
