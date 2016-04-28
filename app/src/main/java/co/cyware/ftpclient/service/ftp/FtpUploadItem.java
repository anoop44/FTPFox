package co.cyware.ftpclient.service.ftp;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Anoop S S on 28/4/16.
 */
public class FtpUploadItem implements Serializable {

    private File fileToUpload;

    private String remoteFileName;

    private String id;

    private boolean isDownloaded;

    private long totalSize;

    private long uploadedSize;

    public FtpUploadItem(File file) {
        fileToUpload = file;
        remoteFileName = fileToUpload.getName();
        id = fileToUpload.getAbsolutePath();
    }

    public File getFileToUpload() {
        return fileToUpload;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public String getId() {
        return id;
    }

    public long getTotalSize() {
        return fileToUpload.length();
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getUploadedSize() {
        return uploadedSize;
    }

    public void setUploadedSize(long uploadedSize) {
        this.uploadedSize = uploadedSize;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
