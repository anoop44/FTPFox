package co.cyware.ftpclient.presenter;

import co.cyware.ftpclient.interactor.FTPInteractor;
import co.cyware.ftpclient.view.FTPView;

/**
 * Created by Anoop S S on 29/4/16.
 */
public abstract class FTPPresenter<VIEW extends FTPView> extends BasePresenter<VIEW> {

    private static final String FTP_SERVER_CONNECTED_TO = "FTP Server : %s";

    public void onResume(){
        getView().showServerName(getServerName());
    }

    protected String getServerName(){
        return String.format(FTP_SERVER_CONNECTED_TO, getInteractor().getServerName());
    }

    abstract FTPInteractor getInteractor();
}
