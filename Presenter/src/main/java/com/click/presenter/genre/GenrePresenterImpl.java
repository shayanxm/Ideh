package com.click.presenter.genre;

import android.content.Context;

import com.click.model.HawkHelper;
import com.click.model.service.ApiService;
import com.click.model.service.ApiUtil;
import com.click.pc.GenreModelResponse;
import com.click.presenter.idea_list.IdeaListPresenterImpl;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GenrePresenterImpl implements GenreContract.GenrePresenter {
    private Context context;
    private GenreContract.GenreView view;
    private Subscription subscription;
    private ApiService apiService;

    public GenrePresenterImpl(Context context, GenreContract.GenreView view) {
        this.context = context;
        this.view = view;
        this.apiService = ApiUtil.getApiService();
    }

    @Override
    public void getGenreList() {
        try {
            String token = HawkHelper.getToken(context);
            subscription = apiService.getGenreList(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new GenreResponse());
        } catch (Exception ex) {
            view.onError(ex.getMessage());
        }
    }

    @Override
    public void logout() {
        try {
            HawkHelper.clearData(context);
            view.naToLogin("عملیات با موفقیت انجام شد");
        }catch (Exception ex){
            view.onError(ex.getMessage());
        }
    }

    private class GenreResponse extends Subscriber<GenreModelResponse> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            handleThrowable(e);
        }

        @Override
        public void onNext(GenreModelResponse response) {
            try {
                if (response.getData().getStatus() != null){
                    view.naToLogin("احراز هویت شما با مشکلی مواجه شده است");
                }else {
                    view.getGenreList(response.getData());
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
