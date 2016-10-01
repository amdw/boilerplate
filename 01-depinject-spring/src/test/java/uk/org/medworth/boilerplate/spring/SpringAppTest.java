package uk.org.medworth.boilerplate.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.C;
import uk.org.medworth.boilerplate.spring.config.Config;

import static org.junit.Assert.assertEquals;

/**
 * Simple integration test to illustrate injecting a mock dependency into the graph
 */
public class SpringAppTest {
    private static final String TEST_FACT = "MyTestFact";
    private static final String TEST_C_SPEECH = "Don't ask me, I'm just a test dummy";

    @Test
    public void testDependencyOverride() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class, TestConfig.class);
        A a = context.getBean(A.class);
        assertEquals("B told me: C says " + TEST_FACT + "; C said: " + TEST_C_SPEECH, a.getASpeech());
    }

    @Configuration
    private static class TestConfig {
        /** Spring requires a visible constructor :S */
        TestConfig() {}

        @Bean
        C c() {
            return new MockC();
        }
    }

    private static class MockC implements C {
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
