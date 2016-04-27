package co.cyware.ftpclient.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    public Context getContext(){
        return this;
    }

    @Override
    public void onClick(View v) {}
}
