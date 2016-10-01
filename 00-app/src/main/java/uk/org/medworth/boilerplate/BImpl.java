package uk.org.medworth.boilerplate;

import javax.inject.Inject;

public class BImpl implements B {
    private final C c;

    @Inject
    public BImpl(C c) {
        System.out.println("Creating " + BImpl.class.getSimpleName());
        this.c = c;
    }

    @Override
    public String getBSpeech() {
        return "C says " + c.getFunFact();
    }
}
