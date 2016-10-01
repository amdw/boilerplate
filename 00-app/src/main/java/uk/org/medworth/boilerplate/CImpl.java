package uk.org.medworth.boilerplate;

import javax.inject.Inject;

public class CImpl implements C {
    private final String s;

    @Inject
    public CImpl(String s) {
        System.out.println("Creating " + CImpl.class.getSimpleName());
        this.s = s;
    }

    @Override
    public String getCSpeech() {
        return "I sell sea shells";
    }

    @Override
    public String getFunFact() {
        return "My shells are C shore shells";
    }
}
