package co.cyware.ftpclient.service.async;


import co.cyware.ftpclient.service.async.runner.AsyncJob;
import co.cyware.ftpclient.service.async.runner.CancellableAsyncRunner;

/**
 * Created by Anoop S S on 12/4/16.
 */
public interface IAsyncJobServices {

    CancellableAsyncRunner runAsyncJob(AsyncJob asyncJob);
}
