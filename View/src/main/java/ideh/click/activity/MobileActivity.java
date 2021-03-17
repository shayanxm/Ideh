package ideh.click.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.click.R;
import com.click.pc.Constant;
import com.click.presenter.verify_code.VerifyCodeContract;
import com.click.presenter.verify_code.VerifyCodePresenterImpl;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ideh.click.view.loading_button.KS_LoadingButton;

public class MobileActivity extends AppCompatActivity implements VerifyCodeContract.VerifyCodeView {
    private VerifyCodeContract.VerifyCodePresenter presenter;

    @BindView(R.id.txtMobile)
    TextInputEditText txtMobile;

    @BindView(R.id.btnSubmit)
    KS_LoadingButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        ButterKnife.bind(this);
        presenter = new VerifyCodePresenterImpl(this, this);
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
        String mobile = txtMobile.getText().toString();

        if (!isValidMobileNumber(mobile)) {
            Toasty.warning(MobileActivity.this, "شماره همراه بدرستی وارد نشده است", Toast.LENGTH_LONG, false).show();
            txtMobile.requestFocus();
            return;
        }

        btnSubmit.startLoading();
        presenter.submit(mobile);
    }

    public static boolean isValidMobileNumber(String input) {
        String pattern = "^09[0-9]{9}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    @Override
    public void onSuccess(String message, String mobile) {
        Intent intent = new Intent(MobileActivity.this, VerifyCodeActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(Constant._MOBILE, mobile);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    @Override
    public void onNetworkError() {
        btnSubmit.stopLoading();
        Toasty.warning(MobileActivity.this, getString(R.string.network_error), Toast.LENGTH_LONG, false).show();
    }

    @Override
    public void onError(String message) {
        btnSubmit.stopLoading();
        Toasty.error(MobileActivity.this, message, Toast.LENGTH_LONG, false).show();
    }
}