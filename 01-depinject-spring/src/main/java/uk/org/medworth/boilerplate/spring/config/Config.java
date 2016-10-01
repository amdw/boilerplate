package uk.org.medworth.boilerplate.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.org.medworth.boilerplate.*;

/**
 * Main configuration class to be shared between production and test code
 */
@Configuration
public class Config {
    @Bean
    A a(C c) {
        return new AImpl(b(c), c);
    }

    @Bean
    B b(C c) {
        return new BImpl(c);
    }
}
