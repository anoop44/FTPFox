package co.cyware.ftpclient.activity;

import android.view.Menu;

import co.cyware.ftpclient.R;

/**
 * Created by Anoop S S on 29/4/16.
 */
public class LogoutActivity extends BaseActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout, menu);
        return true;
    }
}
