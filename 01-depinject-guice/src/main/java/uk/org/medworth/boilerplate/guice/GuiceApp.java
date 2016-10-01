package uk.org.medworth.boilerplate.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.guice.config.Config;
import uk.org.medworth.boilerplate.guice.config.InternalConfig;

public class GuiceApp {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Config(), new InternalConfig());
        A a = injector.getInstance(A.class);
        System.out.println("Getting A's speech:");
        System.out.println(a.getASpeech());
    }
}
