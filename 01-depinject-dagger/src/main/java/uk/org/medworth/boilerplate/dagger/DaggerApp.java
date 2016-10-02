package uk.org.medworth.boilerplate.dagger;

import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.dagger.config.DaggerConfig_Bootstrap;

public class DaggerApp {
    public static void main(String[] args) {
        // The DaggerConfig_Bootstrap class is auto-generated by the Dagger compiler
        A a = DaggerConfig_Bootstrap.builder().build().a();
        System.out.println("A says: " + a.getASpeech());
    }
}
