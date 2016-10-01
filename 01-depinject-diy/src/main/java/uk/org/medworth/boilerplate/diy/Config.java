package uk.org.medworth.boilerplate.diy;

import uk.org.medworth.boilerplate.*;

public class Config {
    private A a;
    private B b;
    private C c;

    A getA() {
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
            c = new CImpl("wibble");
        }
        return c;
    }

    /** Override the default C, e.g. for test purposes */
    void setC(C c) {
        if (this.c != null) {
            throw new IllegalStateException("C already initialized!");
        }
        this.c = c;
    }
}
