package co.cyware.ftpclient.view;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public interface RemoteFileListView extends BaseView {

    void setRemoteFileListAdapter(RecyclerView.Adapter recyclerViewAdapter);

    void updateUpdateProgress(int progress);
}
