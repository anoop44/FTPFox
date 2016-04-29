package co.cyware.ftpclient.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.cyware.ftpclient.R;
import co.cyware.ftpclient.presenter.RemoteFileListPresenter;
import co.cyware.ftpclient.view.RemoteFileListView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class FileListActivity extends LogoutActivity implements RemoteFileListView {

    //Presenter
    private RemoteFileListPresenter mRemoteFileListPresenter;

    //Views
    private RecyclerView mRemoteFileListRecycler;
    private FloatingActionButton mFileUplaodFloatingActionBtn;
    private ProgressBar mFileUploadProgressBar;
    private TextView mServerName;
    private TextView mUploadingFileCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_list);

        mRemoteFileListRecycler = (RecyclerView) findViewById(R.id.ftp_server_file_list_recycler);
        mRemoteFileListRecycler.setHasFixedSize(true);
        mRemoteFileListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mFileUploadProgressBar = (ProgressBar) findViewById(R.id.ftp_file_upload_progress);
        mUploadingFileCount = (TextView) findViewById(R.id.file_upload_list_count);
        mServerName = (TextView) findViewById(R.id.ftp_server_name);

        mFileUplaodFloatingActionBtn = (FloatingActionButton) findViewById(R.id.upload_floating_btn);
        mFileUplaodFloatingActionBtn.setOnClickListener(this);

        mRemoteFileListPresenter = new RemoteFileListPresenter();
        mRemoteFileListPresenter.attachView(this);
    }

    @Override
    public void setRemoteFileListAdapter(RecyclerView.Adapter recyclerViewAdapter) {
        mRemoteFileListRecycler.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void updateUpdateProgress(final int progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFileUploadProgressBar.setProgress(progress);
            }
        });
    }

    @Override
    public void hideUploadingText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUploadingFileCount.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showUploadCount(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUploadingFileCount.setVisibility(View.VISIBLE);
                mUploadingFileCount.setText(text);
            }
        });
    }

    @Override
    public void showServerName(String serverName) {
        mServerName.setText(serverName);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRemoteFileListPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mRemoteFileListPresenter.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.signout:
                mRemoteFileListPresenter.onSignOut();
                break;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_floating_btn:
                mRemoteFileListPresenter.onClickUploadBtn();
                break;
        }
    }
}
