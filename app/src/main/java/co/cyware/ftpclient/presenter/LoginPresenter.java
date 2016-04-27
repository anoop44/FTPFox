package co.cyware.ftpclient.presenter;

import android.text.TextUtils;

import co.cyware.ftpclient.interactor.LoginInteractor;
import co.cyware.ftpclient.view.LoginView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginInteractor mLoginInteractor;

    @Override
    protected void onAttached() {
        super.onAttached();

        mLoginInteractor = new LoginInteractor(this);
    }

    public void onLogin(String serverUrl, String userName, String password) {
        String errorMessage = mLoginInteractor.validateLoginInfo(serverUrl, userName, password);
        if (TextUtils.isEmpty(errorMessage)) {
            mLoginInteractor.connectToFtpServer(serverUrl, userName, password);
        } else {
            showError(errorMessage);
        }
    }

    public void showFtpErrorMessage(String errorMessage) {
        showError(errorMessage);
    }
}
