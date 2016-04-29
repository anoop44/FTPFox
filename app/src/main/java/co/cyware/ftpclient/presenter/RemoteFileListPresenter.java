package co.cyware.ftpclient.presenter;

import android.app.ProgressDialog;

import org.apache.commons.net.ftp.FTPFile;

import co.cyware.ftpclient.activity.FileUploadActivity;
import co.cyware.ftpclient.adapter.RemoteFileListAdapter;
import co.cyware.ftpclient.interactor.RemoteFileListInteractor;
import co.cyware.ftpclient.service.ftp.FtpUploadCallback;
import co.cyware.ftpclient.view.RemoteFileListView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class RemoteFileListPresenter extends FTPPresenter<RemoteFileListView> {

    private static final String DOWNLOADING_FILE = "Downloading file";

    private static final String UPLOADING_FILE_COUNT = "Uploading %d file(s)";

    private RemoteFileListInteractor mRemoteFileListInteracor = null;

    private RemoteFileListAdapter mRemoteFileListAdapter = null;

    private ProgressDialog mDownloadProgressDialog;

    @Override
    protected void onAttached() {
        super.onAttached();

        mRemoteFileListInteracor = new RemoteFileListInteractor(this);
    }

    public void showFilesList(FTPFile[] ftpFiles) {

        getView().hideLoading();

        if (null == mRemoteFileListAdapter) {
            mRemoteFileListAdapter = new RemoteFileListAdapter(mRemoteFileSelectCallback);
            getView().setRemoteFileListAdapter(mRemoteFileListAdapter);
        }

        mRemoteFileListAdapter.setFileList(ftpFiles);
    }

    public void showNoFilesError() {
        getView().hideLoading();
    }

    private void downloadOrShowFile(int position) {

        FTPFile ftpFile = mRemoteFileListAdapter.getItemAtPos(position);

        if (mRemoteFileListInteracor.existLocalFile(ftpFile)) {

        } else {
            mRemoteFileListInteracor.downloadFile(ftpFile);
        }
    }

    private OnRemoteFileSelectCallback mRemoteFileSelectCallback = new OnRemoteFileSelectCallback() {
        @Override
        public void onSelectFileAtPosition(int position) {
            downloadOrShowFile(position);
        }
    };

    public void showLocalFile(FTPFile localFile) {
        hideDownloadProgress();
    }

    public void showDownloadProgress() {

        hideDownloadProgress();

        mDownloadProgressDialog = new ProgressDialog(getContext());
        mDownloadProgressDialog.setCancelable(false);
        mDownloadProgressDialog.setMessage(DOWNLOADING_FILE);
        mDownloadProgressDialog.show();
    }

    public void hideDownloadProgress() {
        if (null != mDownloadProgressDialog) {
            mDownloadProgressDialog.dismiss();
            mDownloadProgressDialog = null;
        }
    }

    private void updateProgressBar(long uploaded, long total) {
        getView().updateUpdateProgress((int) (uploaded * 100 / total));

        int uploadingFileCount = mRemoteFileListInteracor.getUploadingFileCount();
        if (uploadingFileCount == 0) {
            getView().hideUploadingText();
        } else {
            getView().showUploadCount(String.format(UPLOADING_FILE_COUNT, uploadingFileCount));
        }
    }

    public void onResume() {

        getView().showLoading();

        getView().showServerName(mRemoteFileListInteracor.getServerName());

        mRemoteFileListInteracor.getRemoteFileList();

        mRemoteFileListInteracor.registerCallback(mFtpUploadCallback);
    }

    public void onPause() {
        getView().hideLoading();

        mRemoteFileListInteracor.cancelPendingRequest();
        mRemoteFileListInteracor.unregisterCallback(mFtpUploadCallback);
    }

    public void onClickUploadBtn() {
        showNextScreen(FileUploadActivity.class, null);
    }

    private FtpUploadCallback mFtpUploadCallback = new FtpUploadCallback() {
        @Override
        public void onUploadProgress(String id, long uploaded, long total) {
            updateProgressBar(uploaded, total);
        }

        @Override
        public void onUploadComplete(String id) {
            updateProgressBar(100, 100);
        }

        @Override
        public void onUploadError(String id) {
            updateProgressBar(0, 100);
        }

        @Override
        public void onFileRemoved(String id) {
            updateProgressBar(0, 100);
        }
    };

    public interface OnRemoteFileSelectCallback {
        void onSelectFileAtPosition(int position);
    }
}
