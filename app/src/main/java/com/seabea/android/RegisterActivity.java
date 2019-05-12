package com.seabea.android;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.seabea.android.moxyviews.RestView;
import com.seabea.android.moxyviews.VerifableView;
import com.seabea.android.presenters.RestPresenter;
import com.seabea.android.presenters.VerifyPresenter;

public class RegisterActivity extends MvpAppCompatActivity implements RestView, VerifableView, View.OnClickListener {

    Spinner spSelectAccType;
    MaterialEditText metLogin;
    MaterialEditText metPassword;
    MaterialEditText metConfirmPassword;
    MaterialEditText metFirstName;
    MaterialEditText metLastName;
    Spinner spSelectSex;
    MaterialEditText metCity;
    Button btnSignUp;


    @InjectPresenter
    VerifyPresenter presenterVerify;

    @ProvidePresenter
    VerifyPresenter getVerifyPresenter() {
        return new VerifyPresenter();
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
    }

    private void initViews() {
        spSelectAccType = findViewById(R.id.spinner_account_type);
        metLogin = findViewById(R.id.met_login);
        metPassword = findViewById(R.id.met_password);
        metConfirmPassword = findViewById(R.id.met_password_confirm);
        metFirstName = findViewById(R.id.met_name_first);
        metLastName = findViewById(R.id.met_name_last);
        spSelectSex = findViewById(R.id.spinner_sex);
        metCity = findViewById(R.id.met_сity);
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

    }

    @Override
    public void onRestSuccess(String msg) {

    }

    @Override
    public void onRestFailure(String error) {

    }

    @Override
    public void onCorrectInput(String msg) {

    }

    @Override
    public void onIncorrectInput(String error) {

    }
}
