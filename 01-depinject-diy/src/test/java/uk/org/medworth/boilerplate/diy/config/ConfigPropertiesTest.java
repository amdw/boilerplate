package uk.org.medworth.boilerplate.diy.config;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ConfigPropertiesTest {
    private ConfigProperties props;

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        props = ConfigProperties.fromClasspathFile("test.properties");
    }

    @Test
    public void testRequiredStringProperty() {
        assertEquals("thevalue", props.getRequiredString("AProperty"));
    }

    @Test
    public void testMissingProperty() {
        thrown.expect(ConfigProperties.MissingPropertyException.class);
        thrown.expectMessage("test.properties");
        props.getRequiredString("DoesNotExist");
    }

    @Test
    public void testMissingFile() {
        thrown.expect(IllegalArgumentException.class);
        ConfigProperties.fromClasspathFile("doesnotexist.properties");
    }
}
