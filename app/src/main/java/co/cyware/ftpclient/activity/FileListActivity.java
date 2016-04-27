package co.cyware.ftpclient.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import co.cyware.ftpclient.R;
import co.cyware.ftpclient.presenter.RemoteFileListPresenter;
import co.cyware.ftpclient.view.RemoteFileListView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class FileListActivity extends BaseActivity implements RemoteFileListView {

    //Presenter
    private RemoteFileListPresenter mRemoteFileListPresenter;

    //Views
    private RecyclerView mRemoteFileListRecycler;
    private FloatingActionButton mFileUplaodFloatingActionBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_list);

        mRemoteFileListRecycler = (RecyclerView) findViewById(R.id.ftp_server_file_list_recycler);
        mRemoteFileListRecycler.setHasFixedSize(true);
        mRemoteFileListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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
    protected void onResume() {
        super.onResume();

        mRemoteFileListPresenter.refreshRemoteFileList();
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
