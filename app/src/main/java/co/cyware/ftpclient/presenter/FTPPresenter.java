package co.cyware.ftpclient.presenter;

import android.app.ProgressDialog;
import android.content.Intent;

import co.cyware.ftpclient.activity.LoginActivity;
import co.cyware.ftpclient.interactor.FTPInteractor;
import co.cyware.ftpclient.view.FTPView;

/**
 * Created by Anoop S S on 29/4/16.
 */
public abstract class FTPPresenter<VIEW extends FTPView> extends BasePresenter<VIEW> {

    private static final String FTP_SERVER_CONNECTED_TO = "FTP Server : %s";

    private static final String LOGGING_OUT = "Please wait while logout";

    private ProgressDialog mLoginProgressDialog;

    public void onResume() {
        getView().showServerName(getServerName());
    }

    protected String getServerName() {
        return String.format(FTP_SERVER_CONNECTED_TO, getInteractor().getServerName());
    }

    public void onSignOut() {
        showLogoutProgress(LOGGING_OUT);
        getInteractor().signOut();
    }

    public void showLogoutProgress(String message) {

        hideLogoutProgress();

        mLoginProgressDialog = new ProgressDialog(getContext());
        mLoginProgressDialog.setCancelable(false);
        mLoginProgressDialog.setMessage(message);
        mLoginProgressDialog.show();
    }

    public void hideLogoutProgress() {
        if (null != mLoginProgressDialog) {
            mLoginProgressDialog.dismiss();
            mLoginProgressDialog = null;
        }
    }

    abstract FTPInteractor getInteractor();

    public void onSignOutComplete() {

        hideLogoutProgress();

        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(intent);
    }
}
