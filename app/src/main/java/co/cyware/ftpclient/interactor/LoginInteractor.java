package co.cyware.ftpclient.interactor;

import android.text.TextUtils;

import co.cyware.ftpclient.presenter.LoginPresenter;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class LoginInteractor extends BaseInteractor<LoginPresenter> {

    private static final String FTP_SERVER_CANNOT_BE_EMPTY = "FTP server cannot be empty";
    private static final String FTP_USER_NAME_CANNOT_BE_EMPTY = "FTP username cannot be empty";
    private static final String FTP_PASSWORD_CANNOT_BE_EMPTY = "FTP password cannot be empty";

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
}
