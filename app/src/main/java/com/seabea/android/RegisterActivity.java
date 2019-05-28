package com.seabea.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.seabea.android.data.User;
import com.seabea.android.models.RegisterModelImpl;
import com.seabea.android.moxyviews.RegisterVerifableView;
import com.seabea.android.moxyviews.RestView;
import com.seabea.android.presenters.RegisterVerifyPresenter;
import com.seabea.android.presenters.RestPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class RegisterActivity extends MvpAppCompatActivity implements RestView, RegisterVerifableView, View.OnClickListener {

    Spinner spSelectAccType;
    MaterialEditText metEmail;
    MaterialEditText metPassword;
    MaterialEditText metConfirmPassword;
    MaterialEditText metFirstName;
    MaterialEditText metLastName;
    Spinner spSelectSex;
    Button btnSignUp;


    @InjectPresenter
    RegisterVerifyPresenter presenterVerify;

    @ProvidePresenter
    RegisterVerifyPresenter getVerifyPresenter() {
        return new RegisterVerifyPresenter(new RegisterModelImpl());
    }

    @InjectPresenter
    RestPresenter presenter;

    @ProvidePresenter
    RestPresenter getRestPresenter() {
        return new RestPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        initViews();
//        JSONObject jsonObject = null;
//        String access = null;
//        try {
//            jsonObject = new JSONObject("{\"tokens\":{\"access\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6InJudFVPNXZQZ0lpOVdFa2t5OThPaWNaNlZXSzBpWXRfIn0.eyJpc3MiOiJodHRwOlwvXC9yZWFjdC1naXQ6Nzg4OFwvIiwiYXVkIjoiaHR0cDpcL1wvcmVhY3QtZ2l0Ojc4ODhcLyIsImp0aSI6InJudFVPNXZQZ0lpOVdFa2t5OThPaWNaNlZXSzBpWXRfIiwiaWF0IjoxNTU3NzY3MzAzLCJleHAiOjE1NTc3Njc5MDMsInVpZCI6MSwidXNlciI6eyJpZCI6MSwibG9naW4iOiJhZG1pbiIsImZpcnN0X25hbWUiOm51bGwsImxhc3RfbmFtZSI6bnVsbH19.1XV-DNVmJ7Fvksw51Y2_kZs2s1w8PM89Wfdmk9B7w7w\",\"refresh\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6IjJyZzI4QTExNS02WHBGazlVaTc2YzdfZ09tYlljS1lzIn0.eyJpc3MiOiJodHRwOlwvXC9yZWFjdC1naXQ6Nzg4OFwvIiwiYXVkIjoiaHR0cDpcL1wvcmVhY3QtZ2l0Ojc4ODhcLyIsImp0aSI6IjJyZzI4QTExNS02WHBGazlVaTc2YzdfZ09tYlljS1lzIiwiaWF0IjoxNTU3NzY3MzAzLCJleHAiOjE1NjAzNTkzMDMsInVpZCI6MX0.VEZvGmu_olGSvPNRf0VYow5eFdHBLqN0ylbBwGWsmZw\"},\"expire_at\":1557767903}");
//            access = jsonObject.getJSONObject("tokens").getString("access");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String[] accessSplit = new String[0];
//        if (access != null) {
//            accessSplit = access.split("[.]");
//        }
//        String jws = new String(Base64.decode(accessSplit[1],Base64.NO_WRAP));
//        try {
//            jsonObject = new JSONObject(new String(Base64.decode(accessSplit[1],Base64.NO_WRAP)));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void initViews() {
        spSelectAccType = findViewById(R.id.spinner_account_type);
        metEmail = findViewById(R.id.met_email);
        metPassword = findViewById(R.id.met_password);
        metConfirmPassword = findViewById(R.id.met_password_confirm);
        metFirstName = findViewById(R.id.met_name_first);
        metLastName = findViewById(R.id.met_name_last);
        spSelectSex = findViewById(R.id.spinner_sex);
        btnSignUp = findViewById(R.id.b_sign_up);
        setViewMethods();
    }

    private void setViewMethods() {
        btnSignUp.setOnClickListener(this);
        setSpinnerAdapter(spSelectAccType, R.layout.spinner_select_acc_type_item
                , getResources().getStringArray(R.array.spinner_select_acc_type)
                , getIntent().getIntExtra("SELECTED_ACCOUNT_TYPE",0));
        setSpinnerAdapter(spSelectSex, R.layout.spinner_select_acc_type_item
                , getResources().getStringArray(R.array.spinner_select_sex)
                , 0);
    }

    private void setSpinnerAdapter(Spinner spinner, int resource, String[] strings, int selectItem) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, resource, strings);
        adapter.setDropDownViewResource(resource);
        spinner.setAdapter(adapter);
        spinner.post(() -> spinner.setSelection(selectItem));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_sign_up: {
                setEnabledViews(false);
                presenterVerify.checkUserInput(Objects.equals(metEmail.getText(), null) ? null : metEmail.getText().toString()
                    , Objects.equals(metPassword.getText(), null) ? null : metPassword.getText().toString()
                    , Objects.equals(metConfirmPassword.getText(), null) ? null : metConfirmPassword.getText().toString()
                    , Objects.equals(metFirstName.getText(), null) ? null : metFirstName.getText().toString()
                    , Objects.equals(metLastName.getText(), null) ? null : metLastName.getText().toString());
                break;
            }
            default: {
                break;
            }
        }
    }

    private void setEnabledViews(boolean bol) {
        spSelectAccType.setEnabled(bol);
        metEmail.setEnabled(bol);
        metPassword.setEnabled(bol);
        metConfirmPassword.setEnabled(bol);
        metFirstName.setEnabled(bol);
        metLastName.setEnabled(bol);
        spSelectSex.setEnabled(bol);
        btnSignUp.setEnabled(bol);
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

    private void startBusinessActivity() {

    }

    private void startUserActivity() {
        Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRestFailure(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        Log.e("RestAuth", error);
        setEnabledViews(true);
    }

    @Override
    public void onCorrectInput() {
        presenter.signUp(spSelectAccType.getSelectedItemPosition() + 1
                , metEmail.getText().toString(), metPassword.getText().toString()
                , metFirstName.getText().toString(), metLastName.getText().toString()
                , spSelectSex.getSelectedItemPosition() + 1);
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
        metConfirmPassword.setError(error);
    }

    @Override
    public void setFirstNameOnIncorrect(String error) {
        metFirstName.setError(error);
    }

    @Override
    public void setLastNameOnIncorrect(String error) {
        metLastName.setError(error);
    }

}
