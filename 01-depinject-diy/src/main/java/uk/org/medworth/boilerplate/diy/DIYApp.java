package uk.org.medworth.boilerplate.diy;

import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.diy.config.Config;
import uk.org.medworth.boilerplate.diy.config.ConfigProperties;

public class DIYApp {
    public static void main(String[] args) {
        Config config = new Config(ConfigProperties.fromClasspathFile("app.properties"));
        A a = config.getA();
        System.out.println("Getting A's statement:");
        System.out.println(a.getASpeech());
    }
}
