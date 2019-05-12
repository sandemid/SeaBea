package com.seabea.android.models;

public interface RegisterModel {
    String getEmail();
    String getPassword();
    String getConfirmPassword();
    String getFirstName();
    String getLastName();
    void setEmail(String s);
    void setPassword(String s);
    void setConfirmPassword(String s);
    void setFirstName(String s);
    void setLastName(String s);
    void updateModel(String... values);
}
