package uk.org.medworth.boilerplate.dagger.config;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import uk.org.medworth.boilerplate.*;

import javax.inject.Singleton;

@Module
public class Config {
    @Provides @Singleton static A provideA(B b, C c) {
        return new AImpl(b, c);
    }

    @Provides @Singleton static B provideB(C c) {
        return new BImpl(c);
    }

    @Component(modules = InternalConfig.class)
    @Singleton
    public interface Bootstrap {
        A a();
    }
}
