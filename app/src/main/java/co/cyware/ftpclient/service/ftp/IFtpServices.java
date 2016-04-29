package co.cyware.ftpclient.service.ftp;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.util.List;

/**
 * Created by Anoop S S on 27/4/16.
 */
public interface IFtpServices {

    boolean connectToServer(String server, int port);

    boolean login(String userName, String password);

    boolean isConnected();

    FTPFile[] getRemoteFiles();

    void downloadRemoteFile(FTPFile ftpFile, File localFile);

    void registerFtpUploadCallback(FtpUploadCallback ftpUploadCallback);

    void unregisterFtpUploadCallback(FtpUploadCallback ftpUploadCallback);

    void addToQueue(FtpUploadItem ftpUploadItem);

    void removeFromQueue(FtpUploadItem ftpUploadItem);

    List<FtpUploadItem> getUploadingQueue();

    String getServerName();
}
