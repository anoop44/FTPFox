package co.cyware.ftpclient.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import co.cyware.ftpclient.R;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    public Context getContext(){
        return this;
    }

    public void showLoading(){
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        findViewById(R.id.loading).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {}
}
