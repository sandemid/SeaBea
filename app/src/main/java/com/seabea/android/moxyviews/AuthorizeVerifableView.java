package com.seabea.android.moxyviews;

import com.arellomobile.mvp.MvpView;

public interface AuthorizeVerifableView extends MvpView {

    void onCorrectInput();
    void onIncorrectInput(int errorCount);
    void setEmailOnIncorrect(String error);
    void setPasswordOnIncorrect(String error);
    default void setFirstNameOnIncorrect(String error) {
    }
    default void setLastNameOnIncorrect(String error) {
    }

}
