package com.seabea.android.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.seabea.android.models.RegisterModel;
import com.seabea.android.moxyviews.AuthorizeVerifableView;

@InjectViewState
public class RegisterVerifyPresenter extends MvpPresenter <AuthorizeVerifableView> {

    private final int PASSWORD_MIN_LENGTH = 8;
    private final int AUTHORIZE_ACTIVITY_VIEWS = 2;
    private RegisterModel model;
    private int errorCount;

    public RegisterVerifyPresenter(RegisterModel model) {
        this.model = model;
    }

    public void checkUserInputRegister(String... values) {
        errorCount = 0;
        model.updateModel(values);
        checkEmail();
        if (values.length > AUTHORIZE_ACTIVITY_VIEWS) {
            checkPasswords();
            checkFirstName();
            checkLastName();
        } else {
            checkPassword();
        }
        if (errorCount == 0) {
            getViewState().onCorrectInput();
        } else {
            getViewState().onIncorrectInput(errorCount);
        }
    }

    private void checkPassword() {
        if (model.getPassword() == null || model.getPassword().equals("")) {
            getViewState().setPasswordOnIncorrect("Password can't be null");
            errorCount++;
        }
    }


    private void checkLastName() {
        if (model.getLastName() == null || model.getLastName().equals("")) {
            getViewState().setLastNameOnIncorrect("Last name can't be null");
            errorCount++;
        }
    }

    private void checkPasswords() {
        if (model.getPassword() == null || model.getPassword().equals("")) {
            getViewState().setPasswordOnIncorrect("Password can't be null");
            errorCount++;
        } else if (model.getConfirmPassword() == null || model.getConfirmPassword().equals("")
                || !model.getConfirmPassword().equals(model.getPassword())) {
            getViewState().setPasswordOnIncorrect("Password not equals confirm");
            errorCount++;
        } else if (model.getPassword().length() < PASSWORD_MIN_LENGTH) {
            getViewState().setPasswordOnIncorrect("Password must be min 8 characters");
            errorCount++;
        }
    }

    private void checkFirstName() {
        if (model.getFirstName() == null || model.getFirstName().equals("")) {
            getViewState().setFirstNameOnIncorrect("First name can't be null");
            errorCount++;
        }
    }

    private void checkEmail() {
        if (model.getEmail() == null || model.getEmail().equals("")) {
            getViewState().setEmailOnIncorrect("Email can't be null");
            errorCount++;
        } else if (!model.getEmail().contains("@")){
            getViewState().setEmailOnIncorrect("Email must contain @");
            errorCount++;
        }
    }

}
