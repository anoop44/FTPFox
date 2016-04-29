package co.cyware.ftpclient.view;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public interface UploadFileView extends FTPView {

    void setUploadingListAdapter(RecyclerView.Adapter uploadingListAdapter);

    void showBackArrow();
}
