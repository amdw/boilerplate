package uk.org.medworth.boilerplate.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.spring.config.Config;
import uk.org.medworth.boilerplate.spring.config.InternalConfig;

public class SpringApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class, InternalConfig.class);
        A a = context.getBean(A.class);
        System.out.println("Getting A's speech:");
        System.out.println(a.getASpeech());
    }
}
