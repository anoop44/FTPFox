package co.cyware.ftpclient.service.ftp;

/**
 * Created by Anoop S S on 28/4/16.
 */
public interface FtpUploadCallback {

    void onUploadProgress(String id, long uploaded);
}
