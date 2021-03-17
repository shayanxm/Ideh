package ideh.click.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.click.R;
import com.click.pc.Constant;
import com.click.presenter.login.LoginContract;
import com.click.presenter.login.LoginPresenterImpl;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ideh.click.view.loading_button.KS_LoadingButton;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {
    private LoginContract.LoginPresenter presenter;

    @BindView(R.id.txtUserName)
    TextInputEditText txtUserName;

    @BindView(R.id.txtPassword)
    TextInputEditText txtPassword;

    @BindView(R.id.btnSubmit)
    KS_LoadingButton btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        presenter = new LoginPresenterImpl(this, this);
    }

    @OnClick({R.id.btnSubmit,R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                submit();
                break;
            case R.id.btnRegister:
                Intent intent = new Intent(LoginActivity.this, MobileActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void submit() {
        String userName = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        if (userName.matches("")) {
            Toasty.warning(LoginActivity.this, "نام کاربری را وارد نمائید", Toast.LENGTH_LONG, false).show();
            txtUserName.requestFocus();
            return;
        }

        if (password.matches("")) {
            Toasty.warning(LoginActivity.this, "کلمه عبور را وارد نمائید", Toast.LENGTH_LONG, false).show();
            txtPassword.requestFocus();
            return;
        }

        btnSubmit.startLoading();
        presenter.submit(userName, password);
    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(LoginActivity.this, message, Toast.LENGTH_LONG, false).show();

        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(loginIntent);
    }

    @Override
    public void onNetworkError() {
        btnSubmit.stopLoading();
        Toasty.warning(LoginActivity.this, getString(R.string.network_error), Toast.LENGTH_LONG, false).show();
    }

    @Override
    public void onError(String message) {
        btnSubmit.stopLoading();
        Toasty.error(LoginActivity.this, message, Toast.LENGTH_LONG, false).show();
    }
}