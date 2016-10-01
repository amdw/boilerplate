package uk.org.medworth.boilerplate.spring.errors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.spring.config.Config;

public class ErrorDemo {
    public static void main(String[] args) {
        // Note no InternalConfig.class
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        A a = context.getBean(A.class);
        System.out.println(a.getASpeech());
    }
}
