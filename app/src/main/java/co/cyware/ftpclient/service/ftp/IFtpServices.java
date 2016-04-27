package co.cyware.ftpclient.service.ftp;

/**
 * Created by Anoop S S on 27/4/16.
 */
public interface IFtpServices {

    boolean connectToServer(String server, int port);

    boolean login(String userName, String password);
}
