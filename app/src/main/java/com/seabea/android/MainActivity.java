package com.seabea.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.seabea.android.data.User;
import com.seabea.android.moxyviews.RestView;
import com.seabea.android.presenters.RestPresenter;

public class MainActivity extends MvpAppCompatActivity implements RestView {

    @InjectPresenter
    RestPresenter presenter;

    @ProvidePresenter
    RestPresenter getPresenter() {
        return new RestPresenter();
    }

    public static final String KEY_SESSIONID = "SESSION_ID";
    public static final String KEY_USERID = "USER_ID";
    public static final String KEY_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    private String sessionID;
    private int userID;
    private int accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readSavedPreferences();
        checkSavedPreferences();
    }

    private void checkSavedPreferences() {
        if (sessionID == null || userID == 0 || accountType == 0) {
            startAuthorizeActivity();
//            saveToPreference1();
            finish();
        } else {
            presenter.checkAuthorize(String.valueOf(userID), sessionID, String.valueOf(accountType));

        }
    }

    @Override
    public void onRestSuccess(String msg) {
        saveToPreference();
        switch (User.getUser().getUserType()) {
            case 1:
                startUserActivity();
                break;
            case 2:
                startBusinessActivity();
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onRestFailure(String error) {
        startAuthorizeActivity();
        finish();
    }

    @Override
    public void onIncorrectInput(String error) {

    }

    private void startBusinessActivity() {

    }

    private void startUserActivity() {
    }

    private void startAuthorizeActivity() {
        Intent intent = new Intent(MainActivity.this, AuthorizeActivity.class);
        startActivity(intent);
    }

    private void readSavedPreferences() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sessionID = sharedPreferences.getString(KEY_SESSIONID,null);
        userID = sharedPreferences.getInt(KEY_USERID,0);
        accountType = sharedPreferences.getInt(KEY_ACCOUNT_TYPE, 0);
    }

    private void saveToPreference() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SESSIONID, User.getUser().getSessionID());
        editor.putInt(KEY_USERID, User.getUser().getUserID());
        editor.putInt(KEY_ACCOUNT_TYPE, User.getUser().getUserType());
        editor.apply();
    }

//    private void saveToPreference1() {
//        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(KEY_SESSIONID, "qwerty");
//        editor.putInt(KEY_USERID, 12345);
//        editor.putInt(KEY_ACCOUNT_TYPE, 1);
//        editor.apply();
//    }

}
