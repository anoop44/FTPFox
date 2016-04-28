package co.cyware.ftpclient.service.ftp;

import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class FtpServicesImpl implements IFtpServices {

    private FTPClient mFtpClient;

    private FtpFileQueue mFtpFileQueue;

    private List<FtpUploadCallback> mFtpUploadCallbacks;

    public FtpServicesImpl() {

        mFtpClient = new FTPClient();
        mFtpUploadCallbacks = new ArrayList<>();
    }

    @Override
    public boolean connectToServer(String server, int port) {
        boolean isConnectionSuccess = false;

        try {
            mFtpClient.connect(server, port);
            isConnectionSuccess = FTPReply.isPositiveCompletion(mFtpClient.getReplyCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isConnectionSuccess;
    }

    @Override
    public boolean login(String userName, String password) {

        boolean isLoginSuccess = false;
        try {
            isLoginSuccess = mFtpClient.login(userName, password);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isLoginSuccess;
    }

    @Override
    public boolean isConnected() {
        return null != mFtpClient && mFtpClient.isConnected();
    }

    @Override
    public FTPFile[] getRemoteFiles() {

        FTPFile[] files = null;

        try {
            files = mFtpClient.listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    @Override
    public void downloadRemoteFile(FTPFile ftpFile, File localFile) {
        try {

            if (!localFile.exists()) {
                String filePath = localFile.getAbsolutePath();
                String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                localFile.createNewFile();
            }

            InputStream remoteFileInputStream = mFtpClient.retrieveFileStream(ftpFile.getName());
            BufferedInputStream inputStreamReader = new BufferedInputStream(remoteFileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(localFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);


            byte[] data = new byte[1024];
            int delta = 0;

            while ((delta = inputStreamReader.read(data, 0, 1024)) >= 0) {
                bufferedOutputStream.write(data, 0, delta);
            }

            bufferedOutputStream.flush();

            bufferedOutputStream.close();
            remoteFileInputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void registerFtpUploadCallback(FtpUploadCallback ftpUploadCallback) {
        mFtpUploadCallbacks.add(ftpUploadCallback);
    }

    @Override
    public void unregisterFtpUploadCallback(FtpUploadCallback ftpUploadCallback) {
        mFtpUploadCallbacks.remove(ftpUploadCallback);
    }

    @Override
    public void addToQueue(FtpUploadItem ftpUploadItem) {
        if (null == mFtpFileQueue) {
            mFtpFileQueue = new FtpFileQueue(mFtpClient, mFtpUploadCallbacks);
        }

        mFtpFileQueue.addToQueue(ftpUploadItem);
    }

    @Override
    public void removeFromQueue(FtpUploadItem ftpUploadItem) {
        mFtpFileQueue.removeFromQueue(ftpUploadItem);
    }

    @Override
    public List<FtpUploadItem> getUploadingQueue() {
        return mFtpFileQueue == null ? null : mFtpFileQueue.getUploadQueue();
    }


}
