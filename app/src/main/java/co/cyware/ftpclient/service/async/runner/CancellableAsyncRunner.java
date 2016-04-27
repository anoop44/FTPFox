package co.cyware.ftpclient.service.async.runner;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * Created by Anoop S S on 10/11/15.
 * anoopss@schoolspeak.com
 */
public class CancellableAsyncRunner extends AsyncTask<Void, Void, Void> {

    private WeakReference<AsyncJob> mAsyncJob;

    public CancellableAsyncRunner(AsyncJob asyncJob){
        mAsyncJob = new WeakReference<AsyncJob>(asyncJob);
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(mAsyncJob.get()!=null){
            mAsyncJob.get().run();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        AsyncJob asyncJob = mAsyncJob.get();
        if(null != asyncJob && !isCancelled()) {
            asyncJob.finish();
        }
    }

    public void cancel(){
        cancel(true);
    }
}
