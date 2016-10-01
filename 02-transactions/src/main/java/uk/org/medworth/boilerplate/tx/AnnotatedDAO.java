package uk.org.medworth.boilerplate.tx;

import org.springframework.transaction.annotation.Transactional;

/**
 * "Traditional" transactional class using annotation-driven transactions
 */
public class AnnotatedDAO implements DAO {
    @Override
    @Transactional
    public String getValue(String key) {
        throw new RuntimeException("So you can see the stack trace");
    }

    @Override
    @Transactional
    public void saveValue(String key, String value) {
        throw new RuntimeException("So you can see the stack trace");
    }
}
