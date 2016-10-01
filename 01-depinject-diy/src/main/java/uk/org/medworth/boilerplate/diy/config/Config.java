package uk.org.medworth.boilerplate.diy.config;

import uk.org.medworth.boilerplate.*;

public class Config {
    private final ConfigProperties props;
    private A a;
    private B b;
    private C c;

    public Config(ConfigProperties props) {
        this.props = props;
    }

    public A getA() {
        if (a == null) {
            a = new AImpl(getB(), getC());
        }
        return a;
    }

    B getB() {
        if (b == null) {
            b = new BImpl(getC());
        }
        return b;
    }

    private C getC() {
        if (c == null) {
            c = new CImpl(props.getRequiredString("CImpl.speech"));
        }
        return c;
    }

    /** Override the default C, e.g. for test purposes */
    public void setC(C c) {
        if (this.c != null) {
            throw new IllegalStateException("C already initialized!");
        }
        this.c = c;
    }
}
