package com.click.presenter.idea_save;

import android.content.Context;

import com.click.model.HawkHelper;
import com.click.model.service.ApiService;
import com.click.model.service.ApiUtil;
import com.click.pc.Constant;
import com.click.pc.DataInfoModelResponse;
import com.click.pc.ListModel;
import com.click.pc.ResponseModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SaveIdeaPresenterImpl implements SaveIdeaContract.SaveIdeaPresenter {
    private Context context;
    private SaveIdeaContract.SaveIdeaView view;
    private Subscription subscription;
    private ApiService apiService;

    public SaveIdeaPresenterImpl(Context context, SaveIdeaContract.SaveIdeaView view) {
        this.context = context;
        this.view = view;
        this.apiService = ApiUtil.getApiService();
    }

    @Override
    public void getDataInfo() {
        try {
            String token = HawkHelper.getToken(context);
            subscription = apiService.getDataInfo(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ServerResponse());
        } catch (Exception ex) {
            view.onError(ex.getMessage());
        }
    }

    @Override
    public void saveIdea(String title, String description, List<ListModel> selectedGenreList, List<ListModel> selectedKeyWordList,
                         List<ListModel> reviewTitleList, List<ListModel> reviewDescriptionList) {
        try {
            String token = HawkHelper.getToken(context);

            JsonObject params = new JsonObject();
            params.addProperty("idea_name", title);
            params.addProperty("idea_text", description);
            params.addProperty("name", title);

            JsonArray genreArray = new JsonArray();
            for (ListModel listModel : selectedGenreList) {
                genreArray.add(listModel.getTitle());
            }

            JsonArray keyWordArray = new JsonArray();
            for (ListModel listModel : selectedKeyWordList) {
                keyWordArray.add(listModel.getTitle());
            }

            JsonArray reviewTitleArray = new JsonArray();
            for (ListModel listModel : reviewTitleList) {
                reviewTitleArray.add(listModel.getId());
            }

            JsonArray reviewDescriptionArray = new JsonArray();
            for (ListModel listModel : reviewDescriptionList) {
                reviewDescriptionArray.add(listModel.getId());
            }

            params.add("Genere", genreArray);
            params.add("key_word", keyWordArray);
            params.add("MenuItem_idea_name", reviewTitleArray);
            params.add("MenuItem_idea_text", reviewDescriptionArray);

            subscription = apiService.saveIdea(token, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SaveResponse());
        } catch (Exception ex) {
            view.onError(ex.getMessage());
        }
    }

    private class ServerResponse extends Subscriber<DataInfoModelResponse> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            handleThrowable(e);
        }

        @Override
        public void onNext(DataInfoModelResponse response) {
            try {
                if (response.getData().getStatus() != null) {
                    view.navToLogin("احراز هویت شما با مشکلی مواجه شده است");
                } else {
                    view.getDataInfo(response.getData());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                view.onError(ex.getMessage());
            }
        }
    }

    private class SaveResponse extends Subscriber<ResponseModel> {

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
