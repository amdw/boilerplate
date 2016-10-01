package uk.org.medworth.boilerplate.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.org.medworth.boilerplate.C;
import uk.org.medworth.boilerplate.CImpl;

/**
 * Again a separate configuration module for the parts we want to be able to override in tests
 */
@Configuration
public class InternalConfig {
    @Bean
    C c() {
        return new CImpl("wibble");
    }
}
