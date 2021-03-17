package com.click.presenter.idea_list;

import android.content.Context;

import com.click.model.HawkHelper;
import com.click.model.service.ApiService;
import com.click.model.service.ApiUtil;
import com.click.pc.IdeaListModel;
import com.click.pc.IdeaModel;
import com.click.pc.IdeaModelResponse;
import com.click.pc.ListModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IdeaListPresenterImpl implements IdeaListContract.IdeaListPresenter {
    private Context context;
    private IdeaListContract.IdeaListView view;
    private Subscription subscription;
    private ApiService apiService;

    public IdeaListPresenterImpl(Context context, IdeaListContract.IdeaListView view) {
        this.context = context;
        this.view = view;
        this.apiService = ApiUtil.getApiService();
    }

    @Override
    public void getIdeaList(int page, List<ListModel> filter) {
        try {
            String token = HawkHelper.getToken(context);

            JsonObject params = new JsonObject();
            params.addProperty("page", page);

            JsonArray jsonArray = new JsonArray();
            for (ListModel listModel : filter) {
                if (listModel.isSelected()) {
                    jsonArray.add(listModel.getTitle());
                }
            }

            if (jsonArray.size() > 0)
                params.add("genere_filter", jsonArray);

            subscription = apiService.getIdeaList(token, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ServerResponse());

        } catch (Exception ex) {
            view.onError(ex.getMessage());
        }
    }

    private class ServerResponse extends Subscriber<IdeaModelResponse> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            handleThrowable(e);
        }

        @Override
        public void onNext(IdeaModelResponse response) {
            try {
                if (response.getData().getSubCategory3XidOne() == null) {
                    view.naToLogin("احراز هویت شما با مشکلی مواجه شده است");
                } else {
                    List<IdeaListModel> list = createList(response.getData());
                    view.getIdeaList(list, response.getData().getTotalCount_NumberIdeas());
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

    private List<IdeaListModel> createList(IdeaModel model) {
        List<IdeaListModel> list = new ArrayList<>();

        for (int i = 0; i < model.getSubCategory3XidOne().size(); i++) {
            int id = model.getIdeaNumber().get(i);

            String[] titleArray = model.getIdeaTitle().get(i);
            StringBuilder title = new StringBuilder();
            for (String item : titleArray) {
                title.append(item).append(" ");
            }

            String[] descriptionArray = model.getIdeaText().get(i);
            StringBuilder description = new StringBuilder();
            for (String item : descriptionArray) {
                description.append(item).append(" ");
            }

            String[] genreArray = model.getSubCategoryTitlenew().get(i);
            String[] keyWordArray = model.getIdeaKW().get(i);

            IdeaListModel ideaListModel = new IdeaListModel();
            ideaListModel.setId(id);
            ideaListModel.setTitle(title.toString().trim());
            ideaListModel.setDescription(description.toString().trim());
            ideaListModel.setGenreArray(genreArray);
            ideaListModel.setKeyWordArray(keyWordArray);

            list.add(ideaListModel);
        }

        return list;
    }
}
