package com.seabea.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.seabea.android.data.User;
import com.seabea.android.models.RegisterModelImpl;
import com.seabea.android.moxyviews.AuthorizeVerifableView;
import com.seabea.android.moxyviews.RestView;
import com.seabea.android.presenters.RegisterVerifyPresenter;
import com.seabea.android.presenters.RestPresenter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.Objects;

public class AuthorizeActivity extends MvpAppCompatActivity
        implements RestView, AuthorizeVerifableView, View.OnClickListener {

    Spinner spSelectAccType;
    MaterialEditText metEmail;
    MaterialEditText metPassword;
    Button btnSignIn;
    TextView txtSignUp;
    ImageButton btnSignVK;


    @InjectPresenter
    RegisterVerifyPresenter presenterVerify;

    @ProvidePresenter
    RegisterVerifyPresenter getVerifyPresenter() {
        return new RegisterVerifyPresenter(new RegisterModelImpl());
    }

    @InjectPresenter
    RestPresenter presenter;

    @ProvidePresenter
    RestPresenter getPresenter() {
        return new RestPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        initViews();
    }

    private void initViews() {
        txtSignUp = findViewById(R.id.t_sign_up);
        btnSignIn = findViewById(R.id.b_sign_in);
        btnSignVK = findViewById(R.id.ib_sign_vk);
        metEmail = findViewById(R.id.met_email);
        metPassword = findViewById(R.id.met_password);
        spSelectAccType = findViewById(R.id.spinner_account_type);
        setViewMethods();
    }

    private void setViewMethods() {
        btnSignIn.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        btnSignVK.setOnClickListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_select_acc_type_item
                , getResources().getStringArray(R.array.spinner_select_acc_type));
        adapter.setDropDownViewResource(R.layout.spinner_select_acc_type_item);
        spSelectAccType.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_sign_in: {
                setEnabledViews(false);
                presenterVerify.checkUserInput(Objects.equals(metEmail.getText(), null) ? null : metEmail.getText().toString()
                        , Objects.equals(metPassword.getText(), null) ? null : metPassword.getText().toString());
                break;
            }
            case R.id.t_sign_up: {
                startRegisterActivity();
                break;
            }
            case R.id.ib_sign_vk: {
                setEnabledViews(false);
                VKSdk.login(this, VKScope.EMAIL);
                break;
            }
        }
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(AuthorizeActivity.this, RegisterActivity.class);
        intent.putExtra("SELECTED_ACCOUNT_TYPE", spSelectAccType.getSelectedItemPosition());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
                if (res.email == null) {
                    onRestFailure("VK: There is no access to email!");
                    return;
                }
                presenter.sendVKRequest(res, spSelectAccType.getSelectedItemPosition() + 1);
                onRestSuccess("VK token get successful");
            }
            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
                onRestFailure(error.toString());
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setEnabledViews(boolean bol) {
        metEmail.setEnabled(bol);
        metPassword.setEnabled(bol);
        spSelectAccType.setEnabled(bol);
    }

    @Override
    public void onRestSuccess(String msg) {
        Log.i("RestAuth", msg);
        switch (User.getUser().getUserType()) {
            case 1:
                startUserActivity();
                finish();
                break;
            case 2:
                startBusinessActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRestFailure(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        Log.e("RestAuth", error);
        setEnabledViews(true);
    }


    private void startBusinessActivity() {

    }

    private void startUserActivity() {
        Intent intent = new Intent(AuthorizeActivity.this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCorrectInput() {
        presenter.ordinaryEntry(spSelectAccType.getSelectedItemPosition() + 1
                , metEmail.getText().toString(), metPassword.getText().toString());
        Toast.makeText(getApplicationContext(), "Input is correct", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onIncorrectInput(int errorCount) {
        setEnabledViews(true);
        Toast.makeText(getApplicationContext(), "There are many errors in input: " + errorCount, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setEmailOnIncorrect(String error) {
        metEmail.setError(error);
    }

    @Override
    public void setPasswordOnIncorrect(String error) {
        metPassword.setError(error);
    }

}
