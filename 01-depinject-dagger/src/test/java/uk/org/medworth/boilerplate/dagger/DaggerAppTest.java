package uk.org.medworth.boilerplate.dagger;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import org.junit.Test;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.C;
import uk.org.medworth.boilerplate.dagger.config.Config;

import javax.inject.Singleton;

import static org.junit.Assert.assertEquals;

/**
 * Example of an integration test using an overridden implementation of C
 */
public class DaggerAppTest {
    private static final String C_SPEECH = "Nothing to say, I'm just a test dummy";
    private static final String TEST_FACT = "TestFact";

    @Test
    public void testDependencyOverride() {
        // DaggerDaggerAppTest_TestBootstrap is auto-generated: the test equivalent of DaggerConfig_Bootstrap
        A a = DaggerDaggerAppTest_TestBootstrap.builder().build().getA();
        assertEquals("B told me: C says " + TEST_FACT + "; C said: " + C_SPEECH, a.getASpeech());
    }

    /**
     * Mock implementation of C which we want to inject into the graph
     */
    private static class MockC implements C {
        @Override
        public String getFunFact() {
            return TEST_FACT;
        }

        @Override
        public String getCSpeech() {
            return C_SPEECH;
        }
    }

    /**
     * Test version of {@link uk.org.medworth.boilerplate.dagger.config.InternalConfig}
     */
    @Module(includes = Config.class)
    static class TestConfig {
        @Provides @Singleton static C provideC() {
            return new MockC();
        }
    }

    /**
     * Test version of {@link uk.org.medworth.boilerplate.dagger.config.Config.Bootstrap}
     */
    @Component(modules = DaggerAppTest.TestConfig.class)
    @Singleton
    interface TestBootstrap {
        A getA();
    }
}
