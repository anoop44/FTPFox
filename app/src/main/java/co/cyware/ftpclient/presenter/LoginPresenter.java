package co.cyware.ftpclient.presenter;

import android.app.ProgressDialog;
import android.text.TextUtils;

import co.cyware.ftpclient.activity.FileListActivity;
import co.cyware.ftpclient.interactor.LoginInteractor;
import co.cyware.ftpclient.view.LoginView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private static final String LOGGING_IN = "Please wait while login";
    private LoginInteractor mLoginInteractor;

    private ProgressDialog mLoginProgressDialog;

    @Override
    protected void onAttached() {
        super.onAttached();

        mLoginInteractor = new LoginInteractor(this);
    }

    public void onLogin(String serverUrl, String userName, String password) {
        String errorMessage = mLoginInteractor.validateLoginInfo(serverUrl, userName, password);
        if (TextUtils.isEmpty(errorMessage)) {
            showLoginProgress(LOGGING_IN);
            mLoginInteractor.connectToFtpServer(serverUrl, userName, password);
        } else {
            showError(errorMessage);
        }
    }

    public void showFtpErrorMessage(String errorMessage) {
        hideLoginProgress();
        showError(errorMessage);
    }

    public void onConnectionSuccess(String serverUrl, String userName, String password) {

        hideLoginProgress();
        if (getView().isSaveChecked()) {
            mLoginInteractor.saveLoginInfo(serverUrl, userName, password);
        }
        showNextScreen(FileListActivity.class, null);
    }

    public void showLoginProgress(String message) {

        hideLoginProgress();

        mLoginProgressDialog = new ProgressDialog(getContext());
        mLoginProgressDialog.setCancelable(false);
        mLoginProgressDialog.setMessage(message);
        mLoginProgressDialog.show();
    }

    public void hideLoginProgress() {
        if (null != mLoginProgressDialog) {
            mLoginProgressDialog.dismiss();
            mLoginProgressDialog = null;
        }
    }
}
