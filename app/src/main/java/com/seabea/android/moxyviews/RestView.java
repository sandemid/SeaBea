package com.seabea.android.moxyviews;

import com.arellomobile.mvp.MvpView;

public interface RestView extends MvpView {

    void onRestSuccess(String msg);
    void onRestFailure(String error);

}
