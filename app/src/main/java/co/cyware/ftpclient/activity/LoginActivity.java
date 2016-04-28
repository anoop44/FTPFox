package co.cyware.ftpclient.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import co.cyware.ftpclient.R;
import co.cyware.ftpclient.presenter.LoginPresenter;
import co.cyware.ftpclient.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView {

    //Views
    private TextInputLayout mFtpServerInputLayout;
    private TextInputLayout mFtpUserNameInputLayout;
    private TextInputLayout mFtpPasswordInputLayout;
    private CheckBox mFtpLoginRemember;

    //Presenter
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFtpServerInputLayout = (TextInputLayout) findViewById(R.id.ftp_server_address);
        mFtpUserNameInputLayout = (TextInputLayout) findViewById(R.id.ftp_user_name);
        mFtpPasswordInputLayout = (TextInputLayout) findViewById(R.id.ftp_user_password);

        mFtpLoginRemember = (CheckBox) findViewById(R.id.ftp_login_remember);

        findViewById(R.id.login).setOnClickListener(this);

        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);
    }

    @Override
    public boolean isSaveChecked() {
        return mFtpLoginRemember.isChecked();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                mLoginPresenter.onLogin(mFtpServerInputLayout.getEditText().getText().toString(),
                        mFtpUserNameInputLayout.getEditText().getText().toString(),
                        mFtpPasswordInputLayout.getEditText().getText().toString());
                break;
        }
    }
}
