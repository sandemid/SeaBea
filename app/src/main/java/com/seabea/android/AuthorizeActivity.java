package com.seabea.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

public class AuthorizeActivity extends MvpAppCompatActivity
        implements AuthorizeView, View.OnClickListener {

    Spinner spSelectAccType;
    MaterialEditText metLogin;
    MaterialEditText metPassword;
    Button btnSignIn;
    TextView txtSignUp;
    ImageButton btnSignVK;



    @InjectPresenter
    AuthorizePresenter presenter;


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
        metLogin = findViewById(R.id.met_login);
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
                Toast.makeText(this, "R.id.b_sign_in", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.t_sign_up: {
                Toast.makeText(this, "R.id.t_sign_up", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.ib_sign_vk: {
                VKSdk.login(this, VKScope.EMAIL);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                        "sex, bdate, city, country"));
                final String[] userName = new String[2];
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        //Do complete stuff
                        try {
                            userName[0] = response.json.getJSONArray("response").getJSONObject(0).getString("first_name");
                            userName[1] = response.json.getJSONArray("response").getJSONObject(0).getString("last_name");
                            txtSignUp.setText(userName[0] + " " + userName[1] + " " + res.email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(VKError error) {
                        //Do error stuff
                    }
                    @Override
                    public void onProgress(VKRequest.VKProgressType progressType,
                                           long bytesLoaded,
                                           long bytesTotal)
                    {
                        //I don't really believe in progress
                    }
                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                        //More luck next time
                    }
                });
                // User passed Authorization
                txtSignUp.setText(res.email);
            }
            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
