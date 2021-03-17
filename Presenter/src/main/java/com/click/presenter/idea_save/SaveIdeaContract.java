package com.click.presenter.idea_save;

import com.click.pc.DataInfoModel;
import com.click.pc.ListModel;

import java.util.List;

public interface SaveIdeaContract {
    interface SaveIdeaView {
        void getDataInfo(DataInfoModel data);

        void onSuccess(String message);

        void onNetworkError();

        void navToLogin(String message);

        void onError(String message);
    }

    interface SaveIdeaPresenter {
        void getDataInfo();

        void saveIdea(String title, String description, List<ListModel> genreList, List<ListModel> keyWordList,
                      List<ListModel> reviewTitleList, List<ListModel> reviewDescriptionList);
    }
}
