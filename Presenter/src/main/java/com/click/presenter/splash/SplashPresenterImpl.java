package com.click.presenter.splash;

import android.content.Context;

import com.click.model.HawkHelper;
import com.click.pc.Constant;

public class SplashPresenterImpl implements SplashContract.SplashPresenter {
    private Context context;
    private SplashContract.SplashView view;

    public SplashPresenterImpl(Context context, SplashContract.SplashView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void checkLogin() {
        String token = (String) HawkHelper.getData(context, Constant._TOKEN);

        if (token == null || token.matches("") || token.isEmpty()) {
            view.checkLogin(false);
        } else {
            view.checkLogin(true);
        }
    }
}
