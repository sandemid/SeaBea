package com.seabea.android.moxyviews;

public interface RegisterVerifableView extends AuthorizeVerifableView {
    @Override
    void setEmailOnIncorrect(String error);
    @Override
    void setPasswordOnIncorrect(String error);
    @Override
    void setFirstNameOnIncorrect(String error);
    @Override
    void setLastNameOnIncorrect(String error);

}
