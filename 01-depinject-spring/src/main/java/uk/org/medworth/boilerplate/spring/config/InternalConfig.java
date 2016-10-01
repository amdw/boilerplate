package uk.org.medworth.boilerplate.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;
import uk.org.medworth.boilerplate.C;
import uk.org.medworth.boilerplate.CImpl;

import java.io.IOException;

/**
 * Again a separate configuration module for the parts we want to be able to override in tests
 */
@Configuration
public class InternalConfig {
    @Value("${CImpl.speech}")
    String cSpeech;

    @Bean
    C c() {
        return new CImpl(cSpeech);
    }

    /**
     * Without this bean declared, the @Value placeholder above will not be resolved properly.
     *
     * Using @PropertySource by itself will not work; nor will it work if this method is not declared static.
     *
     * See {@link org.springframework.context.annotation.PropertySource} JavaDoc for more info.
     */
    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
        MutablePropertySources sources = new MutablePropertySources();
        sources.addFirst(new ResourcePropertySource("classpath:app.properties"));
        result.setPropertySources(sources);
        result.setIgnoreUnresolvablePlaceholders(false);
        return result;
    }
}
