package uk.org.medworth.boilerplate.tx;

public interface DAO {
    String getValue(String key);
    void saveValue(String key, String value);
}
