package uk.org.medworth.boilerplate.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import org.junit.Test;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.C;
import uk.org.medworth.boilerplate.guice.config.Config;

import javax.inject.Singleton;

import static org.junit.Assert.assertEquals;

public class GuiceAppTest {
    private static final String TEST_FACT = "MyTestFact";
    private static final String TEST_C_SPEECH = "Don't ask me, I'm just a test dummy";

    @Test
    public void testDependencyOverride() {
        Injector injector = Guice.createInjector(new Config(), new TestConfig());
        A a = injector.getInstance(A.class);
        assertEquals("B told me: C says " + TEST_FACT + "; C said: " + TEST_C_SPEECH, a.getASpeech());
    }

    private class TestConfig extends AbstractModule {
        @Override
        protected void configure() {
        }

        @Provides @Singleton
        private C makeC() {
            return new MockC();
        }
    }

    private class MockC implements C {
        @Override
        public String getFunFact() {
            return TEST_FACT;
        }

        @Override
        public String getCSpeech() {
            return TEST_C_SPEECH;
        }
    }
}
