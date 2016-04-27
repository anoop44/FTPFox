package co.cyware.ftpclient.service.async;

import android.os.AsyncTask;

import co.cyware.ftpclient.service.async.runner.AsyncJob;
import co.cyware.ftpclient.service.async.runner.CancellableAsyncRunner;


/**
 * Created by Anoop S S on 12/4/16.
 */
public class AsyncJobServicesImpl implements IAsyncJobServices{

    private static volatile AsyncJobServicesImpl sInstance;

    public static AsyncJobServicesImpl getInstance(){
        if(null == sInstance){
            synchronized (AsyncJobServicesImpl.class){
                sInstance = new AsyncJobServicesImpl();
            }
        }

        return sInstance;
    }

    private AsyncJobServicesImpl(){}

    @Override
    public CancellableAsyncRunner runAsyncJob(AsyncJob asyncJob) {
        CancellableAsyncRunner cancellableAsyncRunner = new CancellableAsyncRunner(asyncJob);
        cancellableAsyncRunner.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
        return cancellableAsyncRunner;
    }
}
