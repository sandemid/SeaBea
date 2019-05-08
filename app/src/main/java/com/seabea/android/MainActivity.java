package com.seabea.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SESSIONID = "SESSION_ID";
    public static final String KEY_USERID = "USER_ID";
    public static final String KEY_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    private String sessionID;
    private int userID;
    private int accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readSavedPreferences();
        checkSavedPreferences();
        finish();
    }

    private void checkSavedPreferences() {
        if (sessionID == null || userID == 0 || accountType == 0) {
            startAuthorizeActivity();
        } else if (checkAuthorizeOnServer()) {
            switch (accountType) {
                case 1:
                    startUserActivity();
                    break;
                case 2:
                    startBusinessActivity();
                    break;
                default:
                    break;
            }
        } else {
            startAuthorizeActivity();
        }
    }

    private void startBusinessActivity() {

    }

    private void startUserActivity() {
    }

    private boolean checkAuthorizeOnServer() {
        return false;
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

}
