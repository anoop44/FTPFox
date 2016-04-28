package co.cyware.ftpclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import co.cyware.ftpclient.R;
import co.cyware.ftpclient.presenter.UploadFilePresenter;
import co.cyware.ftpclient.view.UploadFileView;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class FileUploadActivity extends BaseActivity implements UploadFileView {

    //Presenter
    private UploadFilePresenter mUploadFilePresenter;

    //Views
    private RecyclerView mUploadingFileListRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_layout);

        mUploadingFileListRecycler = (RecyclerView) findViewById(R.id.ftp_upload_file_list);
        mUploadingFileListRecycler.setHasFixedSize(true);
        mUploadingFileListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        findViewById(R.id.select_file_floating_action).setOnClickListener(this);

        mUploadFilePresenter = new UploadFilePresenter();
        mUploadFilePresenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mUploadFilePresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mUploadFilePresenter.onResume();
    }

    @Override
    public void setUploadingListAdapter(RecyclerView.Adapter uploadingListAdapter) {
        mUploadingFileListRecycler.setAdapter(uploadingListAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_file_floating_action:
                mUploadFilePresenter.onClickSelectFile();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mUploadFilePresenter.onIntentResult(requestCode, resultCode, data);
    }
}
