package co.cyware.ftpclient.service;

import android.content.Context;

import co.cyware.ftpclient.service.async.AsyncJobServicesImpl;
import co.cyware.ftpclient.service.async.IAsyncJobServices;
import co.cyware.ftpclient.service.ftp.FtpServicesImpl;
import co.cyware.ftpclient.service.ftp.IFtpServices;
import co.cyware.ftpclient.service.persistence.IPersistenceService;
import co.cyware.ftpclient.service.persistence.PersistenceImpl;

/**
 * Created by Anoop S S on 7/4/16.
 * anoopvvs@gmail.com
 * <p/>
 * Holds instances of various services which are consumed by interactors
 */

public class ServiceHolder {

    private static volatile ServiceHolder sInstance;

    //AsyncRunner instance
    IAsyncJobServices mAsyncJobServices;

    //FTPServices instance
    IFtpServices mFtpServices;

    //Persistence instance
    IPersistenceService mPersistenceServices;

    /**
     * @param context
     * @return Singleton instance of the ServiceHolder class
     */
    public static ServiceHolder getInstance(Context context) {
        if (null == sInstance) {
            synchronized (ServiceHolder.class) {
                sInstance = new ServiceHolder(context);
            }
        }

        return sInstance;
    }

    /**
     * Constructor is made private to make sure that outside classes can't instantiate it to constructor.
     * Thus sticking to the singleton pattern
     *
     * @param context
     */
    private ServiceHolder(Context context) {
        mAsyncJobServices = AsyncJobServicesImpl.getInstance();
        mFtpServices = new FtpServicesImpl();
        mPersistenceServices = new PersistenceImpl(context);
    }

    /**
     * @return Singleton instance of the {@Link IAsyncJobServices} for interactors to carry out long running tasks
     */
    public IAsyncJobServices getAsyncJobServices() {
        return mAsyncJobServices;
    }

    /**
     * @return instance of the {@link IFtpServices} for interactors to carryout ftp operations
     */
    public IFtpServices getFTPServices(){
        return mFtpServices;
    }

    /**
     * @return instance of the {@link IPersistenceService} for interactors to carryout storage operations
     */
    public IPersistenceService getPersistenceServices(){
        return mPersistenceServices;
    }

}
