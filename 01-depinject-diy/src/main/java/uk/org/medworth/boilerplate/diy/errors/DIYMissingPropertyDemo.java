package uk.org.medworth.boilerplate.diy.errors;

import uk.org.medworth.boilerplate.diy.config.ConfigProperties;

/**
 * Demonstration of the clear and simple error message you get when you try to retrieve a non-existent
 * property using the DIY approach. Compare with SpringMissingPropertyDemo.
 */
public class DIYMissingPropertyDemo {
    public static void main(String[] args) {
        ConfigProperties props = ConfigProperties.fromClasspathFile("app.properties");
        props.getRequiredString("Does.Not.Exist");
    }
}
