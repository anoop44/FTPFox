package co.cyware.ftpclient.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import co.cyware.ftpclient.view.BaseView;


/**
 * Created by Anoop S S on 6/4/16.
 */
public class BasePresenter<VIEW extends BaseView> {

    private static final String ACTIVITY_BUNDLE_EXTRA = "ACTIVITY_BUNDLE_EXTRA";

    private Context mContext;

    private VIEW mView;

    /**
     * Attaches a view to its presenter class
     *
     * @param view - View to be attached
     */
    public void attachView(VIEW view) {
        this.mView = view;
        this.mContext = view.getContext();
        onAttached();
    }

    /**
     * Does nothing. other presenters extending it can {@link Override} this to listen {@link #attachView(BaseView)} completion
     */
    protected void onAttached() {
    }


    /**
     * @return the current view attached to the presenter
     */
    public VIEW getView() {
        return mView;
    }

    /**
     * A shortcut method to use {@link Activity} directly than VIEW.getActiviy()
     *
     * @return {@link AppCompatActivity} - reference to current {@link Activity}
     */
    protected AppCompatActivity getActivity() {
        return (AppCompatActivity) getContext();
    }

    /**
     * A shortcut method to use {@link Context} directly than calling VIEW.getContext()
     *
     * @return {@link Context}
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Method to show next scree/activity
     *
     * @param activity      - Class to be displayed - shoule extend activity
     * @param activityExtra - bunble to be passed through the intent
     */
    protected <T extends Activity> void showNextScreen(Class<T> activity, Bundle activityExtra) {
        Intent intent = new Intent(mContext, activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
    }

    /**
     * Method to start an intent to deliver result back
     *
     * @param intent      {@link Intent} - to be started for result
     * @param requestCode unique code to identify a intent, which started for result
     */
    protected void startIntentForResult(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent, requestCode);
    }

    /**
     * @return {@link Bundle} - which is passed through intent to current {@link Activity}
     */
    protected Bundle getBundle() {
        return getActivity().getIntent().getBundleExtra(ACTIVITY_BUNDLE_EXTRA);
    }

    /**
     * Shows general errors and warnings
     *
     * @param errorMsgResId - String resource id of error message to be shown
     */
    protected void showError(int errorMsgResId) {
        Toast.makeText(mContext, errorMsgResId, Toast.LENGTH_LONG).show();
    }

    /**
     * Shows general errors and warnings
     *
     * @param errorMsgResId - String error message to be shown
     */
    protected void showError(String errorMsgResId) {
        Toast.makeText(mContext, errorMsgResId, Toast.LENGTH_LONG).show();
    }

}
