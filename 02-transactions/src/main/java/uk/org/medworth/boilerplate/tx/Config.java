package uk.org.medworth.boilerplate.tx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Configuration
@EnableTransactionManagement
public class Config {
    @Bean(name = "annotatedDAO")
    DAO annotatedDao() {
        return new AnnotatedDAO();
    }

    @Bean(name = "boilerplateDAO")
    DAO boilerplateDao() {
        return new BoilerPlateDAO(transactionManager());
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        TransactionStatus dummyStatus = (TransactionStatus) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{TransactionStatus.class}, ((proxy, method, args) -> {
            if ("isNewTransaction".equals(method.getName())) {
                return true;
            }
            System.out.println("Method invoked on transaction status: " + method.getName());
            return null;
        }));
        InvocationHandler txMgrHandler = (proxy, method, args) -> {
            if ("toString".equals(method.getName())) {
                return "Dummy transaction manager";
            }
            if ("getTransaction".equals(method.getName())) {
                return dummyStatus;
            }
            System.out.println("Method invoked on transaction manager: " + method.getName());
            return null;
        };
        return (PlatformTransactionManager) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{PlatformTransactionManager.class}, txMgrHandler);
    }

}
