package co.cyware.ftpclient.interactor;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.List;

import co.cyware.ftpclient.presenter.UploadFilePresenter;
import co.cyware.ftpclient.service.ftp.FtpUploadCallback;
import co.cyware.ftpclient.service.ftp.FtpUploadItem;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class UploadFileInteractor extends FTPInteractor<UploadFilePresenter> {

    public UploadFileInteractor(UploadFilePresenter presenter) {
        super(presenter);
    }

    public void uploadFile(Uri data) {
        File selectedFile = new File(getRealPathFromURI(data));
        FtpUploadItem ftpUploadItem = new FtpUploadItem(selectedFile);
        getServices().getFTPServices().addToQueue(ftpUploadItem);

        getPresenter().updateList();
    }

    public List<FtpUploadItem> getUploadingList() {
        return getServices().getFTPServices().getUploadingQueue();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = null;
        try {
            Cursor cursor = getPresenter().getContext().getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void cancelUploading(FtpUploadItem ftpUploadItem) {
        getServices().getFTPServices().removeFromQueue(ftpUploadItem);
    }
}
