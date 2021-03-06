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

    private static final String KEY_FTP_SERVER = "KEY_FTP_SERVER";
    private static final String KEY_FTP_USERNAME = "KEY_FTP_USERNAME";
    private static final String KEY_FTP_PASSWORD = "KEY_FTP_PASSWORD";

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
                    getPresenter().onConnectionSuccess(serverUrl, userName, password);
                }
            }
        };

        mConnectionRunner = getServices().getAsyncJobServices().runAsyncJob(asyncFtpConnection);

    }

    public void saveLoginInfo(String serverUrl, String userName, String password) {
        getServices().getPersistenceServices().saveString(KEY_FTP_SERVER, serverUrl);
        getServices().getPersistenceServices().saveString(KEY_FTP_USERNAME, userName);
        getServices().getPersistenceServices().saveString(KEY_FTP_PASSWORD, password);
    }

    public boolean isAlreadyConnected() {
        return getServices().getFTPServices().isConnected();
    }

    public boolean isAutoLoginPresent() {
        return getServices().getPersistenceServices().getString(KEY_FTP_USERNAME) != null && getServices().getPersistenceServices().getString(KEY_FTP_PASSWORD) != null;
    }

    public void autoLogin() {
        connectToFtpServer(getServices().getPersistenceServices().getString(KEY_FTP_SERVER),
                getServices().getPersistenceServices().getString(KEY_FTP_USERNAME),
                getServices().getPersistenceServices().getString(KEY_FTP_PASSWORD));
    }

    public void cancelPendingCallbacks() {
        if (mConnectionRunner != null) {
            mConnectionRunner.cancel();
        }
    }
}
