package uk.org.medworth.boilerplate.diy.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * Simple wrapper for {@link java.util.Properties} which remembers where the properties were loaded from
 * and uses that information in error messages.
 */
public class ConfigProperties {
    private final Properties props;
    private final String source;

    /**
     * Load properties from a file on the classpath.
     */
    public static ConfigProperties fromClasspathFile(String filename) {
        try (InputStream stream = ConfigProperties.class.getClassLoader().getResourceAsStream(filename)) {
            if (stream == null) {
                throw new IllegalArgumentException("Could not load classpath file [" + filename + "]");
            }
            Properties props = new Properties();
            props.load(stream);
            return new ConfigProperties(props, "classpath file [" + filename + "]");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Private constructor: source-specific static factory methods should be used to instantiate.
     */
    private ConfigProperties(Properties props, String source) {
        this.props = props;
        this.source = source;
    }

    /**
     * Get the value of a string-valued property.
     * @throws MissingPropertyException if the property is missing.
     */
    public String getRequiredString(String key) {
        if (!props.containsKey(key)) {
            throw new MissingPropertyException(key, source);
        }
        return props.getProperty(key);
    }

    public static class MissingPropertyException extends RuntimeException {
        MissingPropertyException(String missingKey, String propertySource) {
            super("Key [" + missingKey + "] missing from properties loaded from " + propertySource);
        }
    }
}
