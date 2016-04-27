package co.cyware.ftpclient.interactor;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;

import co.cyware.ftpclient.presenter.RemoteFileListPresenter;
import co.cyware.ftpclient.service.async.runner.AsyncJob;
import co.cyware.ftpclient.service.async.runner.CancellableAsyncRunner;
import co.cyware.ftpclient.utils.FileUtils;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class RemoteFileListInteractor extends BaseInteractor<RemoteFileListPresenter> {

    private static final String DOWNLOAD_FILE_PATH = "%s/%s";

    public RemoteFileListInteractor(RemoteFileListPresenter presenter) {
        super(presenter);
    }

    public void getRemoteFileList() {

        final AsyncJob getRemoteFilesAsync = new AsyncJob() {

            FTPFile[] fTpFiles = null;

            @Override
            public void run() {
                fTpFiles = getServices().getFTPServices().getRemoteFiles();
            }

            @Override
            public void finish() {
                if (null != fTpFiles && fTpFiles.length > 0) {
                    getPresenter().showFilesList(fTpFiles);
                } else {
                    getPresenter().showNoFilesError();
                }
            }
        };

        CancellableAsyncRunner getFiles = getServices().getAsyncJobServices().runAsyncJob(getRemoteFilesAsync);
    }

    public boolean existLocalFile(FTPFile ftpFile) {
        File localFilePath = new File(FileUtils.getDownloadPath(), String.format(DOWNLOAD_FILE_PATH, ftpFile.getUser(), ftpFile.getName()));
        return localFilePath.exists() && ftpFile.getSize() == localFilePath.length();
    }

    public void downloadFile(final FTPFile ftpFile) {

        final File localFile = new File(FileUtils.getDownloadPath(), String.format(DOWNLOAD_FILE_PATH, ftpFile.getUser(), ftpFile.getName()));

        getPresenter().showDownloadProgress();
        AsyncJob downloadAsyncJob = new AsyncJob() {
            @Override
            public void run() {
                getServices().getFTPServices().downloadRemoteFile(ftpFile, localFile);
            }

            @Override
            public void finish() {

                getPresenter().showLocalFile(ftpFile);
            }
        };

        CancellableAsyncRunner cancellableDownloadTask = getServices().getAsyncJobServices().runAsyncJob(downloadAsyncJob);
    }
}