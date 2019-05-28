package com.seabea.android.presenters;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.seabea.android.data.User;
import com.seabea.android.moxyviews.RestView;
import com.seabea.android.rest.SeaBeaRepo;
import com.seabea.android.rest.entities.CheckAuthRequestRestModel;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class RestPresenter extends MvpPresenter<RestView> {

    private final String VK_REQUEST_FIELDS = "sex, bdate, city, country";
    private final String SOCIAL_NETWORK_VK = "VK";

    public RestPresenter() {

    }

    public void checkAuthorize (String userID, String sessionID, String accountType) {
        SeaBeaRepo.getSingleton().getAPI().checkAuthorize(userID, sessionID, accountType)
                .enqueue(new Callback<CheckAuthRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CheckAuthRequestRestModel> call,
                                           @NonNull Response<CheckAuthRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            if (response.body().sessionID.equals("0")) {
                                getViewState().onRestFailure("Authorize error");
                            } else {
                                updateUserFields(response.body());
                                getViewState().onRestSuccess("Authorize successful");
                            }
                        } else {
                            getViewState().onRestFailure("Authorize error");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CheckAuthRequestRestModel> call,@NonNull Throwable t) {
                        getViewState().onRestFailure("Authorize error");
                    }
        });
    }

    public void sendVKRequest(VKAccessToken res, int userType){
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                VK_REQUEST_FIELDS));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //Do complete stuff
                VKApiUserFull user = ((VKList<VKApiUserFull>)response.parsedModel).get(0);
                updateUserFields(res, user, userType);
                getViewState().onRestSuccess("VK authorize successful");
                socialNetworkEntry();
            }
            @Override
            public void onError(VKError error) {
                //Do error stuff
                getViewState().onRestFailure(error.toString());
            }
            @Override
            public void onProgress(VKRequest.VKProgressType progressType,
                                   long bytesLoaded,
                                   long bytesTotal) {
                //I don't really believe in progress
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                //More luck next time
                getViewState().onRestFailure("VK: attemptFailed!");
            }
        });
    }

    private void socialNetworkEntry() {
        User.getUser().clearUserFields();//delete later
        SeaBeaRepo.getSingleton().getAPI()
                .socialEntry(String.valueOf(User.getUser().getUserType()),
                        String.valueOf(User.getUser().getUserSocialID()),
                        !Objects.equals(User.getUser().getSocialNetworkName(), null) ? User.getUser().getSocialNetworkName() : String.valueOf(0),
                        !Objects.equals(User.getUser().getUserFirstName(), null) ? User.getUser().getUserFirstName() : String.valueOf(0),
                        !Objects.equals(User.getUser().getUserLastName(), null) ? User.getUser().getUserLastName() : String.valueOf(0),
                        !Objects.equals(User.getUser().getUserDateBirth(), null) ? User.getUser().getUserDateBirth() : String.valueOf(0),
                        String.valueOf(User.getUser().getUserSex()),
                        !Objects.equals(User.getUser().getUserCity(), null) ? User.getUser().getUserCity() : String.valueOf(0),
                        !Objects.equals(User.getUser().getUserCountry(), null) ? User.getUser().getUserCountry() : String.valueOf(0),
                        !Objects.equals(User.getUser().getUserEmail(), null) ? User.getUser().getUserEmail() : String.valueOf(0))
                .enqueue(new Callback<CheckAuthRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CheckAuthRequestRestModel> call,
                                           @NonNull Response<CheckAuthRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            getViewState().onRestSuccess("Server authorization successful");
                        } else {
                            getViewState().onRestFailure("Server authorization error!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CheckAuthRequestRestModel> call,@NonNull Throwable t) {
                        getViewState().onRestFailure("Server authorization error!");
                    }
                });
    }

    public void signUp (int userType, String email, String password
                        , String firstName, String lastName, int sex) {
        User.getUser().setUserType(1);
        SeaBeaRepo.getSingleton().getAPI()
                .userRegister(String.valueOf(userType), email, password, firstName, lastName, String.valueOf(sex))
                .enqueue(new Callback<CheckAuthRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CheckAuthRequestRestModel> call,
                                           @NonNull Response<CheckAuthRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            updateUserFields(response.body());
                            getViewState().onRestSuccess("Server authorization successful");
                        } else {
                            getViewState().onRestSuccess("Server authorization successful");
//                            getViewState().onRestFailure("Server authorization error!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CheckAuthRequestRestModel> call,@NonNull Throwable t) {
                        getViewState().onRestSuccess("Server authorization successful");
//                        getViewState().onRestFailure("Server authorization error!");
                    }
                });
    }

    public void ordinaryEntry (int userType, String email, String password) {
        User.getUser().setUserType(1);
        SeaBeaRepo.getSingleton().getAPI()
                .ordinaryEntry(String.valueOf(userType), email, password)
                .enqueue(new Callback<CheckAuthRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CheckAuthRequestRestModel> call,
                                           @NonNull Response<CheckAuthRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            updateUserFields(response.body());
                            getViewState().onRestSuccess("Server authorization successful");
                        } else {
                            getViewState().onRestSuccess("Server authorization successful");
//                            getViewState().onRestFailure("Server authorization error!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CheckAuthRequestRestModel> call,@NonNull Throwable t) {
                        getViewState().onRestSuccess("Server authorization successful");
//                        getViewState().onRestFailure("Server authorization error!");
                    }
                });
    }

    private void updateUserFields(CheckAuthRequestRestModel response) {
        User.getUser().clearUserFields();
        User.getUser().setSessionID(response.sessionID);
        User.getUser().setSocialNetworkName(response.social.socialNetworkName);
        User.getUser().setUserSocialID(response.social.socialNetworkID);
        User.getUser().setUserID(response.userid);
        User.getUser().setUserSex(response.sex);
        User.getUser().setUserCity(response.city);
        User.getUser().setUserCountry(response.country);
        User.getUser().setUserDateBirth(response.dateBirth);
        User.getUser().setUserEmail(response.email);
        User.getUser().setUserType(response.userType);
        User.getUser().setUserFirstName(response.name.firstName);
        User.getUser().setUserLastName(response.name.lastName);
    }

    private void updateUserFields(VKAccessToken token, VKApiUserFull user, int userType) {
        User.getUser().clearUserFields();
        User.getUser().setUserType(userType);
        User.getUser().setSocialNetworkName(SOCIAL_NETWORK_VK);
        User.getUser().setUserSocialID(user.id);
        User.getUser().setUserSex(user.sex);
        User.getUser().setUserCity(user.city.title);
        User.getUser().setUserCountry(user.country.title);
        User.getUser().setUserDateBirth(user.bdate);
        User.getUser().setUserEmail(token.email);
        User.getUser().setUserFirstName(user.first_name);
        User.getUser().setUserLastName(user.last_name);
    }

}
