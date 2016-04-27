package co.cyware.ftpclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_layout);

        findViewById(R.id.select_file_floating_action).setOnClickListener(this);

        mUploadFilePresenter = new UploadFilePresenter();
        mUploadFilePresenter.attachView(this);
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
