package uk.org.medworth.boilerplate.guice.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import uk.org.medworth.boilerplate.C;
import uk.org.medworth.boilerplate.CImpl;

import javax.inject.Singleton;

/**
 * Separate module for the parts we want to be able to override in tests
 */
public class InternalConfig extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides @Singleton
    private C makeC() {
        return new CImpl("wibble");
    }
}
