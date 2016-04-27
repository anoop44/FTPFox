package co.cyware.ftpclient.service.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

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
        return false;
    }
}
