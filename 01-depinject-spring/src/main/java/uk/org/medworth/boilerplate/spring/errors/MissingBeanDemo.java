package uk.org.medworth.boilerplate.spring.errors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.org.medworth.boilerplate.A;
import uk.org.medworth.boilerplate.spring.config.Config;

/**
 * Demonstration of what happens when you forget to declare a bean in Spring
 */
public class MissingBeanDemo {
    public static void main(String[] args) {
        // Note no InternalConfig.class so we get a (very long) complaint that there is no C bean
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        A a = context.getBean(A.class);
        System.out.println(a.getASpeech());
    }
}
