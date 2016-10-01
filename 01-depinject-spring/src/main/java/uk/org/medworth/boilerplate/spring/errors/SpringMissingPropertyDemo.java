package uk.org.medworth.boilerplate.spring.errors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * Demonstration of the lack of helpful information in Spring error messages when there is a missing property.
 * If you run the program, you will see that the configuration file name does not appear anywhere in the error output.
 * If this were a real production program, it would be your job to trawl through and find it below - and ensure
 * the property has not been overridden anywhere else, e.g. on the command line.
 */
public class SpringMissingPropertyDemo {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(WrongConfig.class);
        WrongConfig cfg = context.getBean(WrongConfig.class);
        System.out.println(cfg.value);
    }

    @Configuration
    static class WrongConfig {
        @Value("${Property.Doesnt.Exist}")
        String value;

        /**
         * Without this method, e.g. if you try to use @PropertySource("classpath:app.properties") on the
         * WrongConfig class, you don't even get an error at all - try it! The behaviour is the same as the below
         * with setIgnoreUnresolvablePlaceholders(true).
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
}
