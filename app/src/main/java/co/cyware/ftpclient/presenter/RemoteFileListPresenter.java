package co.cyware.ftpclient.presenter;

import android.app.ProgressDialog;

import org.apache.commons.net.ftp.FTPFile;

import co.cyware.ftpclient.adapter.RemoteFileListAdapter;
import co.cyware.ftpclient.interactor.RemoteFileListInteractor;
import co.cyware.ftpclient.view.RemoteFileListView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class RemoteFileListPresenter extends BasePresenter<RemoteFileListView> {

    private static final String DOWNLOADING_FILE = "Downloading file";

    private RemoteFileListInteractor mRemoteFileListInteracor = null;

    private RemoteFileListAdapter mRemoteFileListAdapter = null;

    private ProgressDialog mDownloadProgressDialog;

    @Override
    protected void onAttached() {
        super.onAttached();

        mRemoteFileListInteracor = new RemoteFileListInteractor(this);
    }

    public void refreshRemoteFileList() {
        getView().showLoading();
        mRemoteFileListInteracor.getRemoteFileList();
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

    public interface OnRemoteFileSelectCallback {
        void onSelectFileAtPosition(int position);
    }
}
