package uk.org.medworth.boilerplate.diy;

import org.junit.Test;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.C;

import static org.junit.Assert.assertEquals;

/**
 * Trivial integration test to demonstrate how test doubles can be injected into the object graph
 * using this technique
 */
public class DIYAppTest {
    @Test
    public void testDependencyOverride() {
        Config config = new Config();
        C myC = new MockC();
        config.setC(myC);
        A a = config.getA();
        assertEquals("B told me: C says " + myC.getFunFact() + "; C said: " + myC.getCSpeech(), a.getASpeech());
    }

    private static class MockC implements C {
        @Override
        public String getFunFact() {
            return "TestFact";
        }

        @Override
        public String getCSpeech() {
            return "Nothing to say, I'm just a test dummy";
        }
    }
}
