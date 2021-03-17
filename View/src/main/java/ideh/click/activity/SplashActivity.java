package ideh.click.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.click.R;
import com.click.presenter.splash.SplashContract;
import com.click.presenter.splash.SplashPresenterImpl;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity implements SplashContract.SplashView {
    private SplashContract.SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        presenter = new SplashPresenterImpl(this, this);
        presenter.checkLogin();
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                break;
            case R.id.btnRegister:
                startActivity(new Intent(SplashActivity.this, MobileActivity.class));
                break;
        }
    }

    @Override
    public void checkLogin(boolean isLogin) {
        if (isLogin) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }
}