package ideh.click.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.click.R;
import com.click.pc.Constant;
import com.click.presenter.register.RegisterContract;
import com.click.presenter.register.RegisterPresenterImpl;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ideh.click.view.loading_button.KS_LoadingButton;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView {
    private String mobile;
    private String code;
    private RegisterContract.RegisterPresenter presenter;

    @BindView(R.id.txtEmail)
    TextInputEditText txtEmail;

    @BindView(R.id.txtUserName)
    TextInputEditText txtUserName;

    @BindView(R.id.txtPassword)
    TextInputEditText txtPassword;

    @BindView(R.id.btnSubmit)
    KS_LoadingButton btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        presenter = new RegisterPresenterImpl(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobile = bundle.getString(Constant._MOBILE);
            code = bundle.getString(Constant._CODE);
        }
    }

    @OnClick({R.id.btnSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                submit();
                break;
        }
    }

    private void submit() {
        String email = txtEmail.getText().toString();
        String userName = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        if (!isValidEmail(email)) {
            Toasty.warning(RegisterActivity.this, "آدرس پست الکترونیکی را بدرستی وارد نمائید", Toast.LENGTH_LONG, false).show();
            txtEmail.requestFocus();
            return;
        }

        if (userName.matches("")) {
            Toasty.warning(RegisterActivity.this, "نام کاربری را وارد نمائید", Toast.LENGTH_LONG, false).show();
            txtUserName.requestFocus();
            return;
        }

        if (password.matches("")) {
            Toasty.warning(RegisterActivity.this, "کلمه عبور را وارد نمائید", Toast.LENGTH_LONG, false).show();
            txtPassword.requestFocus();
            return;
        }

        btnSubmit.startLoading();
        presenter.submit(email, userName, password, mobile, code);
    }

    public static boolean isValidEmail(String emailStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(RegisterActivity.this, message, Toast.LENGTH_LONG, false).show();

        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(loginIntent);
    }

    @Override
    public void onNetworkError() {
        btnSubmit.stopLoading();
        Toasty.warning(RegisterActivity.this, getString(R.string.network_error), Toast.LENGTH_LONG, false).show();
    }

    @Override
    public void onError(String message) {
        btnSubmit.stopLoading();
        Toasty.error(RegisterActivity.this, message, Toast.LENGTH_LONG, false).show();
    }
}