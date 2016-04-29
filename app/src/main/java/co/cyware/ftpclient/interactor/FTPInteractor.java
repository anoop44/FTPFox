package co.cyware.ftpclient.interactor;

import co.cyware.ftpclient.presenter.FTPPresenter;
import co.cyware.ftpclient.service.ftp.FtpUploadCallback;

/**
 * Created by Anoop S S on 29/4/16.
 */
public class FTPInteractor<PRESENTER extends FTPPresenter> extends BaseInteractor<PRESENTER>{

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
}
