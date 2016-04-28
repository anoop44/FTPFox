package co.cyware.ftpclient.service.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anoop S S on 28/4/16.
 */
public class FtpFileQueue {

    private final FTPClient mFtpClient;
    private final List<FtpUploadCallback> mFtpUploadCallbacks;

    private FtpUploadTask mFtpUploadTask = null;

    private List<FtpUploadItem> mUploadList;

    long previouslyUploaded = 0;

    public FtpFileQueue(FTPClient ftpClient, List<FtpUploadCallback> ftpUploadCallbacks) {

        this.mFtpClient = ftpClient;
        this.mFtpClient.setCopyStreamListener(mCopyStreamListener);
        this.mFtpUploadCallbacks = ftpUploadCallbacks;
        mUploadList = new ArrayList<>();
    }

    public void addToQueue(FtpUploadItem ftpUploadItem) {

        if (mFtpUploadTask == null) {
            uploadItem(ftpUploadItem);
        }

        mUploadList.add(ftpUploadItem);
    }

    public void removeFromQueue(FtpUploadItem ftpUploadItem) {
        mUploadList.remove(ftpUploadItem);
    }

    private void uploadNext() {

        mFtpUploadTask = null;

        if (mUploadList.size() > 0) {
            for (FtpUploadItem ftpUploadItem : mUploadList) {
                if (!ftpUploadItem.isDownloaded()) {
                    uploadItem(ftpUploadItem);
                    break;
                }
            }
        }
    }

    private void uploadItem(FtpUploadItem ftpUploadItem) {

        previouslyUploaded = 0;

        if (null != mFtpUploadTask) {
            mFtpUploadTask.cancel(true);
            mFtpUploadTask = null;
        }
        mFtpUploadTask = new FtpUploadTask(mFtpClient, ftpUploadItem, mCompletionCallback);
        mFtpUploadTask.execute();
    }

    public String getCurrentFileId() {
        return mFtpUploadTask.getFileId();
    }

    public List<FtpUploadItem> getUploadQueue() {
        return mUploadList;
    }

    public void updateUploadingItem(long totalBytesTransferred) {
        String id = getCurrentFileId();
        for (FtpUploadItem ftpUploadItem : mUploadList) {
            if (ftpUploadItem.getId().equals(id)) {
                ftpUploadItem.setUploadedSize(totalBytesTransferred);
                break;
            }
        }
    }

    private FTPUploadTaskCompletionCallback mCompletionCallback = new FTPUploadTaskCompletionCallback() {
        @Override
        public void onCompletion(FtpUploadItem ftpUploadItem) {
            for (FtpUploadItem ftpUploadListItem : mUploadList) {
                if (ftpUploadListItem.getId().equals(ftpUploadItem.getId())) {
                    ftpUploadItem.setDownloaded(true);
                    break;
                }
            }

            if (mFtpUploadCallbacks.size() > 0) {

                for (FtpUploadCallback ftpUploadCallback : mFtpUploadCallbacks) {
                    ftpUploadCallback.onUploadComplete(getCurrentFileId());
                }
            }

            uploadNext();
        }

        @Override
        public void onError(FtpUploadItem ftpUploadItem) {

            removeFromQueue(ftpUploadItem);
            uploadNext();

            if (mFtpUploadCallbacks.size() > 0) {

                for (FtpUploadCallback ftpUploadCallback : mFtpUploadCallbacks) {
                    ftpUploadCallback.onUploadError(null);
                }
            }

        }
    };

    private CopyStreamListener mCopyStreamListener = new CopyStreamListener() {

        @Override
        public void bytesTransferred(CopyStreamEvent event) {
        }

        @Override
        public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {

            if ((totalBytesTransferred - previouslyUploaded) > (100 * 1000)) {

                updateUploadingItem(totalBytesTransferred);

                if (mFtpUploadCallbacks.size() > 0) {

                    for (FtpUploadCallback ftpUploadCallback : mFtpUploadCallbacks) {
                        ftpUploadCallback.onUploadProgress(getCurrentFileId(), totalBytesTransferred, streamSize);
                    }
                }

                previouslyUploaded = totalBytesTransferred;
            }
        }
    };

    public interface FTPUploadTaskCompletionCallback {

        void onCompletion(FtpUploadItem ftpUploadItem);

        void onError(FtpUploadItem ftpUploadItem);
    }
}
