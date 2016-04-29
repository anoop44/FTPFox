package co.cyware.ftpclient.interactor;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.util.List;

import co.cyware.ftpclient.presenter.RemoteFileListPresenter;
import co.cyware.ftpclient.service.async.runner.AsyncJob;
import co.cyware.ftpclient.service.async.runner.CancellableAsyncRunner;
import co.cyware.ftpclient.service.ftp.FtpUploadCallback;
import co.cyware.ftpclient.service.ftp.FtpUploadItem;
import co.cyware.ftpclient.utils.FileUtils;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class RemoteFileListInteractor extends FTPInteractor<RemoteFileListPresenter> {

    private static final String DOWNLOAD_FILE_PATH = "%s/%s";

    private CancellableAsyncRunner mGetFilesAsyncJob;

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

        mGetFilesAsyncJob = getServices().getAsyncJobServices().runAsyncJob(getRemoteFilesAsync);
    }


    public boolean existLocalFile(FTPFile ftpFile) {
        File localFilePath = getLocalFile(ftpFile);
        return localFilePath.exists() && ftpFile.getSize() == localFilePath.length();
    }

    public void downloadFile(final FTPFile ftpFile) {

        final File localFile = getLocalFile(ftpFile);

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

    public File getLocalFile(FTPFile ftpFile){
        return new File(FileUtils.getDownloadPath(), String.format(DOWNLOAD_FILE_PATH, ftpFile.getUser(), ftpFile.getName()));
    }

    public void cancelPendingRequest() {
        if (null != mGetFilesAsyncJob) {
            mGetFilesAsyncJob.cancel();
        }
    }

    public int getUploadingFileCount() {
        int count = 0;
        List<FtpUploadItem> uploadingFiles = getServices().getFTPServices().getUploadingQueue();
        if (null != uploadingFiles && uploadingFiles.size() > 0) {
            for (FtpUploadItem ftpUploadItem : uploadingFiles) {
                if (!ftpUploadItem.isDownloaded()) {
                    count++;
                }
            }
        }
        return count;
    }

}
