package uk.org.medworth.boilerplate;

import javax.inject.Inject;

public class CImpl implements C {
    private final String speech;

    @Inject
    public CImpl(String speech) {
        System.out.println("Creating " + CImpl.class.getSimpleName());
        this.speech = speech;
    }

    @Override
    public String getCSpeech() {
        return speech;
    }

    @Override
    public String getFunFact() {
        return "My shells are C shore shells";
    }
}
