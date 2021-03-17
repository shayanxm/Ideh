package com.click.presenter.check_code;

import android.content.Context;

import com.click.model.service.ApiService;
import com.click.model.service.ApiUtil;
import com.click.pc.Constant;
import com.click.pc.ResponseModel;
import com.click.presenter.verify_code.VerifyCodePresenterImpl;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckCodePresenterImpl implements CheckCodeContract.CheckCodePresenter {
    private Context context;
    private CheckCodeContract.CheckCodeView view;
    private Subscription subscription;
    private ApiService apiService;

    public CheckCodePresenterImpl(Context context, CheckCodeContract.CheckCodeView view) {
        this.context = context;
        this.view = view;
        this.apiService = ApiUtil.getApiService();
    }

    @Override
    public void submit(String mobile, String code) {
        try {
            subscription = apiService.checkCode(mobile, code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ServerResponse());
        } catch (Exception ex) {
            view.onError(ex.getMessage());
        }
    }

    @Override
    public void sendCode(String mobile) {
        try {
            subscription = apiService.verifyCode(mobile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SendCodeResponse());
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
                    view.onSuccess(responseModel.getData().getMessage(),
                            responseModel.getData().getMobile_number(),
                            responseModel.getData().getCode_Verifi());
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                view.onError(ex.getMessage());
            }
        }
    }

    private class SendCodeResponse extends Subscriber<ResponseModel> {

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
                    view.onSuccessSendCode(responseModel.getData().getMessage());
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
