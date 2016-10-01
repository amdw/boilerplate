package uk.org.medworth.boilerplate.dagger.config;

import dagger.Module;
import dagger.Provides;
import uk.org.medworth.boilerplate.C;
import uk.org.medworth.boilerplate.CImpl;

import javax.inject.Singleton;

/**
 * Dagger recommends having separate modules for "published" versus "internal" bindings.
 *
 * Published bindings are used outside the module and are shared by production and test code;
 * internal bindings are internal to the module and can be overridden in tests.
 *
 * http://google.github.io/dagger/testing.html
 */
@Module(includes = Config.class)
public class InternalConfig {
    @Provides @Singleton static C provideC() {
        return new CImpl("wibble");
    }
}
