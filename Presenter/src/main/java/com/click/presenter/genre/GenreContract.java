package com.click.presenter.genre;

import com.click.pc.GenreModel;
import com.click.pc.IdeaModel;

public interface GenreContract {
    interface GenreView {
        void getGenreList(GenreModel data);

        void onNetworkError();

        void naToLogin(String message);

        void onError(String message);
    }

    interface GenrePresenter {
        void getGenreList();
        void logout();
    }
}
