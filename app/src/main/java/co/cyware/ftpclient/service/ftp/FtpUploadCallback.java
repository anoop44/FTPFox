package co.cyware.ftpclient.service.ftp;

/**
 * Created by Anoop S S on 28/4/16.
 */
public interface FtpUploadCallback {

    void onUploadProgress(String id, long uploaded, long total);

    void onUploadComplete(String id);

    void onUploadError(String id);

    void onFileRemoved(String id);
}
