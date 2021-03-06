package co.cyware.ftpclient.presenter;

import android.content.Intent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.cyware.ftpclient.adapter.UploadingListAdapter;
import co.cyware.ftpclient.interactor.FTPInteractor;
import co.cyware.ftpclient.interactor.UploadFileInteractor;
import co.cyware.ftpclient.service.ftp.FtpUploadCallback;
import co.cyware.ftpclient.view.UploadFileView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class UploadFilePresenter extends FTPPresenter<UploadFileView> {

    private static final String MIME_TYPE_ALL_FILES = "file/*";
    private static final int FILE_CHOOSER_REQUEST_CODE = 1001;
    private static final int RESULT_OK = -1;

    private UploadFileInteractor mUploadFileInteractor;

    private UploadingListAdapter mUplaodingFileListAdapter;

    @Override
    protected FTPInteractor getInteractor() {
        return mUploadFileInteractor;
    }

    @Override
    protected void onAttached() {
        mUploadFileInteractor = new UploadFileInteractor(this);

        mUplaodingFileListAdapter = new UploadingListAdapter(mOnCancelUploadingFileListener);

        getView().setUploadingListAdapter(mUplaodingFileListAdapter);

        getView().showBackArrow();

    }

    public void onResume() {
        super.onResume();

        mUploadFileInteractor.registerCallback(mFtpUploadCallback);
        updateList();
    }

    public void onPause() {
        mUploadFileInteractor.unregisterCallback(mFtpUploadCallback);
    }

    public void onClickSelectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(MIME_TYPE_ALL_FILES);
        startIntentForResult(intent, FILE_CHOOSER_REQUEST_CODE);
    }

    public void onIntentResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case FILE_CHOOSER_REQUEST_CODE:
                    mUploadFileInteractor.uploadFile(data.getData());
                    break;
            }
        }
    }

    public void updateList() {
        mUplaodingFileListAdapter.setUploadFileList(mUploadFileInteractor.getUploadingList());
    }

    private void updateList(String id, long uploaded) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateList();
            }
        });

    }

    private FtpUploadCallback mFtpUploadCallback = new FtpUploadCallback() {
        @Override
        public void onUploadProgress(String id, long uploaded, long total) {
            updateList(id, uploaded);
        }

        @Override
        public void onUploadComplete(String id) {
            updateList(null, 0);
        }

        @Override
        public void onUploadError(String id) {
            updateList(null, 0);
        }

        @Override
        public void onFileRemoved(String id) {
            updateList(null, 0);
        }
    };

    private OnCancelUploadingFileListener mOnCancelUploadingFileListener = new OnCancelUploadingFileListener() {
        @Override
        public void onCancelFileAtPosition(int position) {
            mUploadFileInteractor.cancelUploading(mUplaodingFileListAdapter.getItemAtPos(position));
        }
    };

    public interface OnCancelUploadingFileListener {
        void onCancelFileAtPosition(int position);
    }

}
