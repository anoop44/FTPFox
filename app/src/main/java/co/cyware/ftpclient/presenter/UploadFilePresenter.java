package co.cyware.ftpclient.presenter;

import android.content.Intent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.cyware.ftpclient.adapter.UploadingListAdapter;
import co.cyware.ftpclient.interactor.UploadFileInteractor;
import co.cyware.ftpclient.service.ftp.FtpUploadCallback;
import co.cyware.ftpclient.view.UploadFileView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class UploadFilePresenter extends BasePresenter<UploadFileView> {

    private static final String MIME_TYPE_ALL_FILES = "file/*";
    private static final int FILE_CHOOSER_REQUEST_CODE = 1001;

    private UploadFileInteractor mUploadFileInteractor;

    private UploadingListAdapter mUplaodingFileListAdapter;

    @Override
    protected void onAttached() {
        mUploadFileInteractor = new UploadFileInteractor(this);

        mUplaodingFileListAdapter = new UploadingListAdapter();

        getView().setUploadingListAdapter(mUplaodingFileListAdapter);

    }

    public void onResume() {
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

        switch (requestCode) {

            case FILE_CHOOSER_REQUEST_CODE:
                mUploadFileInteractor.uploadFile(data.getData());
                break;
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
        public void onUploadProgress(String id, long uploaded) {
            updateList(id, uploaded);
        }
    };

}
