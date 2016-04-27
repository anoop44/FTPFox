package co.cyware.ftpclient.service.async.runner;

/**
 * Created by Anoop S S on 10/11/15.
 * anoopss@schoolspeak.com
 */
public interface AsyncJob {

    void run();

    void finish();
}
