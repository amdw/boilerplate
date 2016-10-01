package uk.org.medworth.boilerplate.tx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionalApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        DAO annotated = (DAO) context.getBean("annotatedDAO");
        DAO boilerplate = (DAO) context.getBean("boilerplateDAO");

        System.out.println("Trying annotated DAO (type " + annotated.getClass() + "):");
        try {
            annotated.getValue("wibble");
        } catch (Exception e) {
            System.out.println("Caught exception from annotated DAO:");
            e.printStackTrace(System.out);
        }

        System.out.println();

        System.out.println("Trying boilerplate DAO (type " + boilerplate.getClass() + "):");
        try {
            boilerplate.getValue("wibble");
        } catch (Exception e) {
            System.out.println("Caught exception from boilerplate DAO:");
            e.printStackTrace(System.out);
        }
    }
}
