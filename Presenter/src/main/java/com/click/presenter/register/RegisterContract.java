package com.click.presenter.register;

public interface RegisterContract {
    interface RegisterView{
        void onSuccess(String message);

        void onNetworkError();

        void onError(String message);
    }

    interface RegisterPresenter{
        void submit(String email, String userName, String password, String mobile, String code);
    }
}
