package com.click.presenter.verify_code;

public interface VerifyCodeContract {
    interface VerifyCodeView {
        void onSuccess(String message, String mobile);

        void onNetworkError();

        void onError(String message);
    }

    interface VerifyCodePresenter {
        void submit(String mobile);
    }
}
