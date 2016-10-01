package uk.org.medworth.boilerplate.diy;

import uk.org.medworth.boilerplate.A;

public class DIYApp {
    public static void main(String[] args) {
        Config config = new Config();
        A a = config.getA();
        System.out.println("Getting A's statement:");
        System.out.println(a.getASpeech());
    }
}
