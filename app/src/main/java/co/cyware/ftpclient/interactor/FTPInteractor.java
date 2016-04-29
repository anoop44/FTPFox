package co.cyware.ftpclient.interactor;

import co.cyware.ftpclient.presenter.FTPPresenter;
import co.cyware.ftpclient.service.async.runner.AsyncJob;
import co.cyware.ftpclient.service.ftp.FtpUploadCallback;

/**
 * Created by Anoop S S on 29/4/16.
 */
public class FTPInteractor<PRESENTER extends FTPPresenter> extends BaseInteractor<PRESENTER> {

    public FTPInteractor(PRESENTER presenter) {
        super(presenter);
    }

    public String getServerName() {
        return getServices().getFTPServices().getServerName();
    }

    public void registerCallback(FtpUploadCallback ftpUploadCallback) {
        getServices().getFTPServices().registerFtpUploadCallback(ftpUploadCallback);
    }

    public void unregisterCallback(FtpUploadCallback ftpUploadCallback) {
        getServices().getFTPServices().unregisterFtpUploadCallback(ftpUploadCallback);
    }

    public void signOut() {

        AsyncJob logoutTask = new AsyncJob() {
            @Override
            public void run() {
                getServices().getFTPServices().signOut();
                getServices().getPersistenceServices().clearAll();
            }

            @Override
            public void finish() {
                getPresenter().onSignOutComplete();
            }
        };

        getServices().getAsyncJobServices().runAsyncJob(logoutTask);
    }
}
