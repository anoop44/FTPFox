package co.cyware.ftpclient.interactor;

import android.net.Uri;

import java.io.File;
import java.util.List;

import co.cyware.ftpclient.presenter.UploadFilePresenter;
import co.cyware.ftpclient.service.ftp.FtpUploadCallback;
import co.cyware.ftpclient.service.ftp.FtpUploadItem;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class UploadFileInteractor extends BaseInteractor<UploadFilePresenter> {

    public UploadFileInteractor(UploadFilePresenter presenter) {
        super(presenter);
    }

    public void uploadFile(Uri data) {
        File selectedFile = new File(data.getPath());
        FtpUploadItem ftpUploadItem = new FtpUploadItem(selectedFile);
        getServices().getFTPServices().addToQueue(ftpUploadItem);

        getPresenter().updateList();
    }

    public void registerCallback(FtpUploadCallback ftpUploadCallback) {
        getServices().getFTPServices().registerFtpUploadCallback(ftpUploadCallback);
    }

    public void unregisterCallback(FtpUploadCallback ftpUploadCallback) {
        getServices().getFTPServices().unregisterFtpUploadCallback(ftpUploadCallback);
    }

    public List<FtpUploadItem> getUploadingList() {
        return getServices().getFTPServices().getUploadingQueue();
    }
}
