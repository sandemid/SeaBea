package com.seabea.android.moxyviews;

import com.arellomobile.mvp.MvpView;

public interface VerifableView extends MvpView {
    void onCorrectInput(String msg);
    void onIncorrectInput(String error);
}
