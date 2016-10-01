package uk.org.medworth.boilerplate.guice.config;

import com.google.inject.AbstractModule;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.AImpl;
import uk.org.medworth.boilerplate.B;
import uk.org.medworth.boilerplate.BImpl;

/**
 * Module to be shared between production and test code
 */
public class Config extends AbstractModule {
    @Override
    protected void configure() {
        bind(A.class).to(AImpl.class);
        bind(B.class).to(BImpl.class);
    }
}
