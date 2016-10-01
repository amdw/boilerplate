package uk.org.medworth.boilerplate.tx;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Uses Spring's "programmatic transaction management". I prefer this approach
 * because it avoids the use of AOP. For example, this avoids the notorious
 * difference between self-invocation and external invocation.
 */
public class BoilerPlateDAO implements DAO {
    private final TransactionTemplate transactionTemplate;

    public BoilerPlateDAO(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public String getValue(String key) {
        return transactionTemplate.execute(status -> {
            throw new RuntimeException("So you can see the stack trace");
        });
    }

    @Override
    public void saveValue(String key, String value) {
        transactionTemplate.execute(status -> {
            throw new RuntimeException("So you can see the stack trace");
        });
    }
}
