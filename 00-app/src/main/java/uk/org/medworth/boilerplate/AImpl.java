package uk.org.medworth.boilerplate;

import javax.inject.Inject;

public class AImpl implements A {
    private final B b;
    private final C c;

    @Inject
    public AImpl(B b, C c) {
        System.out.println("Creating " + AImpl.class.getSimpleName());
        this.b = b;
        this.c = c;
    }

    @Override
    public String getASpeech() {
        return "B told me: " + b.getBSpeech() + "; C said: " + c.getCSpeech();
    }
}
