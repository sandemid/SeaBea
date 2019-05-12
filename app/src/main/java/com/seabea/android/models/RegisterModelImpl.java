package com.seabea.android.models;

import java.util.ArrayList;
import java.util.List;

public class RegisterModelImpl implements RegisterModel {

    private final int AUTHORIZE_ACTIVITY_VIEWS = 2;
    private final int LIST_CAPACITY = 5;
    private final int INDEX_EMAIL = 0;
    private final int INDEX_PASSWORD = 1;
    private final int INDEX_PASSWORD_CONFIRM = 2;
    private final int INDEX_FIRST_NAME = 3;
    private final int INDEX_LAST_NAME = 4;

    private List<String> mList;

    public RegisterModelImpl() {
        mList = new ArrayList<>(LIST_CAPACITY);
        for (int i = 0; i < LIST_CAPACITY; i++) {
            mList.add(null);
        }
    }

    @Override
    public String getEmail() {
        return mList.get(INDEX_EMAIL);
    }

    @Override
    public String getPassword() {
        return mList.get(INDEX_PASSWORD);
    }

    @Override
    public String getConfirmPassword() {
        return mList.get(INDEX_PASSWORD_CONFIRM);
    }

    @Override
    public String getFirstName() {
        return mList.get(INDEX_FIRST_NAME);
    }

    @Override
    public String getLastName() {
        return mList.get(INDEX_LAST_NAME);
    }

    @Override
    public void setEmail(String s) {
        mList.set(INDEX_EMAIL , s);
    }

    @Override
    public void setPassword(String s) {
        mList.set(INDEX_PASSWORD , s);
    }

    @Override
    public void setConfirmPassword(String s) {
        mList.set(INDEX_PASSWORD_CONFIRM , s);
    }

    @Override
    public void setFirstName(String s) {
        mList.set(INDEX_FIRST_NAME , s);
    }

    @Override
    public void setLastName(String s) {
        mList.set(INDEX_LAST_NAME , s);
    }

    @Override
    public void updateModel(String... values) {
        setEmail(values[INDEX_EMAIL]);
        setPassword(values[INDEX_PASSWORD]);
        if (values.length > AUTHORIZE_ACTIVITY_VIEWS) {
            setConfirmPassword(values[INDEX_PASSWORD_CONFIRM]);
            setFirstName(values[INDEX_FIRST_NAME]);
            setLastName(values[INDEX_LAST_NAME]);
        }
    }

}
