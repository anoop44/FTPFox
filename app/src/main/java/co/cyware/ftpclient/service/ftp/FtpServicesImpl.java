package co.cyware.ftpclient.service.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class FtpServicesImpl implements IFtpServices {

    private FTPClient mFtpClient;

    public FtpServicesImpl() {
        mFtpClient = new FTPClient();
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
}
