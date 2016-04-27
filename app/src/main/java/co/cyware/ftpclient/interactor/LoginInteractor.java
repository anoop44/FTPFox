package co.cyware.ftpclient.interactor;

import android.text.TextUtils;

import java.io.IOException;

import co.cyware.ftpclient.presenter.LoginPresenter;
import co.cyware.ftpclient.service.async.runner.AsyncJob;
import co.cyware.ftpclient.service.async.runner.CancellableAsyncRunner;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class LoginInteractor extends BaseInteractor<LoginPresenter> {

    private static final String FTP_SERVER_CANNOT_BE_EMPTY = "FTP server cannot be empty";
    private static final String FTP_USER_NAME_CANNOT_BE_EMPTY = "FTP username cannot be empty";
    private static final String FTP_PASSWORD_CANNOT_BE_EMPTY = "FTP password cannot be empty";
    private static final String UNABLE_TO_CONNECT_TO_THE_SERVER = "Unable to connect to the FTP server";
    private static final String UNABLE_TO_LOGIN_TO_THE_SERVER = "Unable to login to the FTP server";

    private CancellableAsyncRunner mConnectionRunner;

    public LoginInteractor(LoginPresenter presenter) {
        super(presenter);
    }

    /**
     * Validate the ftp login info entered
     *
     * @param serverUrl
     * @param userName
     * @param password
     * @return {@link String} - null if there is no error, else returns error message to be displayed to the user
     */
    public String validateLoginInfo(String serverUrl, String userName, String password) {
        if (TextUtils.isEmpty(serverUrl)) {
            return FTP_SERVER_CANNOT_BE_EMPTY;
        } else if (TextUtils.isEmpty(userName)) {
            return FTP_USER_NAME_CANNOT_BE_EMPTY;
        } else if (TextUtils.isEmpty(password)) {
            return FTP_PASSWORD_CANNOT_BE_EMPTY;
        }
        return null;
    }

    /**
     * Its tries to connect to the entered ftp server using given user credentials
     *
     * @param serverUrl
     * @param userName
     * @param password
     */
    public void connectToFtpServer(final String serverUrl, final String userName, final String password) {

        AsyncJob asyncFtpConnection = new AsyncJob() {

            String errorMessage = null;

            @Override
            public void run() {

                if (getServices().getFTPServices().connectToServer(serverUrl, 21)) {
                    if (!getServices().getFTPServices().login(userName, password)) {
                        errorMessage = UNABLE_TO_LOGIN_TO_THE_SERVER;
                    }
                } else {
                    errorMessage = UNABLE_TO_CONNECT_TO_THE_SERVER;
                }
            }

            @Override
            public void finish() {
                if (!TextUtils.isEmpty(errorMessage)) {
                    getPresenter().showFtpErrorMessage(errorMessage);
                } else {
                    getPresenter().onConnectionSuccess();
                }
            }
        };

        mConnectionRunner = getServices().getAsyncJobServices().runAsyncJob(asyncFtpConnection);

    }
}
