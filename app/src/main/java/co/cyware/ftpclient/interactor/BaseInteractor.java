package co.cyware.ftpclient.interactor;


import java.net.UnknownHostException;
import java.util.Collection;

import co.cyware.ftpclient.presenter.BasePresenter;
import co.cyware.ftpclient.service.ServiceHolder;

/**
 * Created by Anoop S S on 8/4/16.
 */
public class BaseInteractor<PRESENTER extends BasePresenter> {

    public static final String USER_JWT_TOKEN = "USER_JWT_TOKEN";

    private static final String SUCCESS = "success";
    private static final String INTERNET_CONNECTION_ISNT_WORKING = "We\'re unable to reach server. Please check whether your internet connection is working";
    private static final String AUTH_TOKEN_FORMAT = " JWT %s";

    private PRESENTER mPresenter;

    private ServiceHolder mServiceHolder;


    public BaseInteractor(PRESENTER presenter) {
        mPresenter = presenter;
        mServiceHolder = ServiceHolder.getInstance(presenter.getContext());
    }

    /**
     * @return {@link PRESENTER} - for interactors to invoke methods in presenter
     */
    PRESENTER getPresenter() {
        return mPresenter;
    }

    /**
     * @return {@link ServiceHolder} instance for Interactors to access various services
     */
    protected ServiceHolder getServices() {
        return mServiceHolder;
    }

    /**
     * Method that converts an internal {@link Exception} to displayable message for API errors
     *
     * @param throwable - value obtained from network callback
     * @return {@link String} message that is to be displayed to the user
     */
    protected String getErrorMessage(Throwable throwable) {
        String errorMessage = null;
        if (throwable instanceof UnknownHostException) {
            errorMessage = INTERNET_CONNECTION_ISNT_WORKING;
        }
        return errorMessage;
    }


    /**
     * @param collection
     * @param <T>
     * @return {@link Boolean} whether the collection is empty or not
     */
    protected <T> boolean isEmptyCollection(Collection<T> collection) {
        return null == collection || collection.size() == 0;
    }


}
