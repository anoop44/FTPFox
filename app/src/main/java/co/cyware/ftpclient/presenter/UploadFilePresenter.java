package co.cyware.ftpclient.presenter;

import android.content.Intent;

import co.cyware.ftpclient.view.UploadFileView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class UploadFilePresenter extends BasePresenter<UploadFileView> {

    private static final String MIME_TYPE_ALL_FILES = "file/*";
    private static final int FILE_CHOOSER_REQUEST_CODE = 1001;

    public void onClickSelectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(MIME_TYPE_ALL_FILES);
        startIntentForResult(intent, FILE_CHOOSER_REQUEST_CODE);
    }

    public void onIntentResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){

            case FILE_CHOOSER_REQUEST_CODE:

                break;
        }
    }
}
