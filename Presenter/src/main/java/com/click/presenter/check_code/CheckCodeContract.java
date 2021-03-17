package com.click.presenter.check_code;

public interface CheckCodeContract {
    interface CheckCodeView {
        void onSuccess(String message, String mobile, String code);

        void onSuccessSendCode(String message);

        void onNetworkError();

        void onError(String message);
    }

    interface CheckCodePresenter {
        void submit(String mobile, String code);

        void sendCode(String mobile);
    }
}
