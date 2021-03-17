package com.click.presenter.login;

import android.content.Context;

import com.click.model.HawkHelper;
import com.click.model.service.ApiService;
import com.click.model.service.ApiUtil;
import com.click.pc.Constant;
import com.click.pc.ResponseModel;
import com.click.presenter.check_code.CheckCodePresenterImpl;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginContract.LoginPresenter {
    private Context context;
    private LoginContract.LoginView view;
    private Subscription subscription;
    private ApiService apiService;

    public LoginPresenterImpl(Context context, LoginContract.LoginView view) {
        this.context = context;
        this.view = view;
        this.apiService = ApiUtil.getApiService();
    }

    @Override
    public void submit(String userName, String password) {
        try {
            subscription = apiService.login(userName, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ServerResponse());
        } catch (Exception ex) {
            view.onError(ex.getMessage());
        }
    }

    private class ServerResponse extends Subscriber<ResponseModel> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            handleThrowable(e);
        }

        @Override
        public void onNext(ResponseModel responseModel) {
            try {
                if (responseModel.getData().getStatus().equals(Constant.ERROR)) {
                    view.onError(responseModel.getData().getMessage());
                } else {
                    HawkHelper.setData(context, Constant._TOKEN, responseModel.getData().getAuth_token());
                    view.onSuccess(responseModel.getData().getMessage());
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                view.onError(ex.getMessage());
            }
        }
    }

    private void handleThrowable(Throwable e) {
        if (e instanceof IOException) {
            view.onNetworkError();
        } else if (e instanceof HttpException) {
            try {
                HttpException httpException = ((HttpException) e);


            } catch (Exception ex) {
                view.onError(ex.getMessage());
            }
        }
    }
}
