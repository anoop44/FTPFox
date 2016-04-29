package co.cyware.ftpclient.service.persistence;


/**
 * Created by Anoop S S on 20/12/15.
 * anoopvvs@gmail.com
 */
public interface IPersistenceService{

    void saveString(String key, String value);

    String getString(String key);

    void clearAll();
}
