package com.click.presenter.verify_code;

import android.content.Context;

import com.click.model.HawkHelper;
import com.click.model.service.ApiService;
import com.click.model.service.ApiUtil;
import com.click.pc.Constant;
import com.click.pc.ResponseModel;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VerifyCodePresenterImpl implements VerifyCodeContract.VerifyCodePresenter {
    private Context context;
    private VerifyCodeContract.VerifyCodeView view;
    private Subscription subscription;
    private ApiService apiService;

    public VerifyCodePresenterImpl(Context context, VerifyCodeContract.VerifyCodeView view) {
        this.context = context;
        this.view = view;
        this.apiService = ApiUtil.getApiService();
    }

    @Override
    public void submit(String mobile) {
        try {
            subscription = apiService.verifyCode(mobile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ServerResponse(mobile));
        } catch (Exception ex) {
            view.onError(ex.getMessage());
        }
    }

    private class ServerResponse extends Subscriber<ResponseModel> {
        private String mobile;

        public ServerResponse(String mobile) {
            this.mobile = mobile;
        }

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
                    HawkHelper.setData(context, Constant._MOBILE, mobile);
                    view.onSuccess(responseModel.getData().getMessage(), mobile);
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
