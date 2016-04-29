package co.cyware.ftpclient.service.ftp;

import android.os.AsyncTask;

import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Anoop S S on 28/4/16.
 */
public class FtpUploadTask extends AsyncTask<Void, Void, Boolean> {

    private final FtpUploadItem mFtpUploadItem;
    private final FTPClient mFtpClient;
    private final FtpFileQueue.FTPUploadTaskCompletionCallback mCompletionCallback;

    public FtpUploadTask(FTPClient ftpClient, FtpUploadItem ftpUploadItem) {
        this.mFtpClient = ftpClient;
        this.mFtpUploadItem = ftpUploadItem;
        this.mCompletionCallback = null;
    }

    public FtpUploadTask(FTPClient ftpClient, FtpUploadItem ftpUploadItem, FtpFileQueue.FTPUploadTaskCompletionCallback completionCallback) {
        this.mFtpClient = ftpClient;
        this.mFtpUploadItem = ftpUploadItem;
        this.mCompletionCallback = completionCallback;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        boolean isSuccess = true;

        try {
            FileInputStream fileInputStream = new FileInputStream(mFtpUploadItem.getFileToUpload());
            mFtpClient.storeFile(mFtpUploadItem.getRemoteFileName(), fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);

        if (isSuccess) {
            mCompletionCallback.onCompletion(mFtpUploadItem);
        } else {
            mCompletionCallback.onError(mFtpUploadItem);
        }
    }

    public String getFileId() {
        return mFtpUploadItem.getId();
    }

    public long getFileSize() {
        return mFtpUploadItem.getTotalSize();
    }

    public FtpUploadItem getUploadingFile() {
        return mFtpUploadItem;
    }
}
