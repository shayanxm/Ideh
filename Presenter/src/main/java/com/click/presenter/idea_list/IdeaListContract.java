package com.click.presenter.idea_list;

import com.click.pc.IdeaListModel;
import com.click.pc.ListModel;

import java.util.List;

public
interface IdeaListContract {
    interface IdeaListView {
        void getIdeaList(List<IdeaListModel> message, int ideaNumber);

        void onNetworkError();

        void naToLogin(String message);

        void onError(String message);
    }

    interface IdeaListPresenter {
        void getIdeaList(int page, List<ListModel> filter);
    }
}
