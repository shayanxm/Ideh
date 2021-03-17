package ideh.click.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.click.R;
import com.click.pc.Constant;
import com.click.presenter.check_code.CheckCodeContract;
import com.click.presenter.check_code.CheckCodePresenterImpl;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ideh.click.view.loading_button.KS_LoadingButton;

public class VerifyCodeActivity extends AppCompatActivity implements CheckCodeContract.CheckCodeView {
    private String mobile;
    private CountDownTimer timer;
    private CheckCodeContract.CheckCodePresenter presenter;

    @BindView(R.id.txtCode)
    TextInputEditText txtCode;

    @BindView(R.id.btnSubmit)
    KS_LoadingButton btnSubmit;

    @BindView(R.id.btnResend)
    KS_LoadingButton btnResend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        ButterKnife.bind(this);
        presenter = new CheckCodePresenterImpl(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobile = bundle.getString(Constant._MOBILE);
        }

        timer = new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long time) {
                long minute = time / (60 * 1000) % 60;
                long seconds = time / 1000 % 60;

                btnResend.setEnabled(false);
                btnResend.setmText("ارسال مجدد کد تا " + minute + ":" + seconds);
            }

            @Override
            public void onFinish() {
                btnResend.setEnabled(true);
                btnResend.setmText("ارسال مجدد کد تایید");
            }
        }.start();
    }

    @OnClick({R.id.btnSubmit, R.id.btnResend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                submit();
                break;
            case R.id.btnResend:
                btnResend.setEnabled(true);
                btnResend.startLoading();
                presenter.sendCode(mobile);
                break;
        }
    }

    private void submit() {
        String code = txtCode.getText().toString();

        if (code.matches("")) {
            Toasty.warning(VerifyCodeActivity.this, "کد تایید را وارد نمائید", Toast.LENGTH_LONG, false).show();
            txtCode.requestFocus();
            return;
        }

        btnSubmit.startLoading();
        presenter.submit(mobile, code);
    }

    @Override
    public void onSuccess(String message, String mobile, String code) {
        Toasty.success(VerifyCodeActivity.this, message, Toast.LENGTH_LONG, false).show();

        Intent intent = new Intent(VerifyCodeActivity.this, RegisterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(Constant._MOBILE, mobile);
        bundle.putString(Constant._CODE, code);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessSendCode(String message) {
        Toasty.success(VerifyCodeActivity.this, message, Toast.LENGTH_LONG, false).show();

        btnResend.setEnabled(false);
        btnResend.stopLoading();
        timer.start();
    }

    @Override
    public void onNetworkError() {
        btnSubmit.stopLoading();
        Toasty.warning(VerifyCodeActivity.this, getString(R.string.network_error), Toast.LENGTH_LONG, false).show();
    }

    @Override
    public void onError(String message) {
        btnSubmit.stopLoading();
        Toasty.error(VerifyCodeActivity.this, message, Toast.LENGTH_LONG, false).show();
    }
}