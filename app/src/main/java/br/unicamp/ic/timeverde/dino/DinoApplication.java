package br.unicamp.ic.timeverde.dino;

import android.app.Application;
import android.content.SharedPreferences;

import br.unicamp.ic.timeverde.dino.helper.Constants;
import br.unicamp.ic.timeverde.dino.model.User;

public class DinoApplication extends Application {

    private static DinoApplication mApplication;
    private User mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = DinoApplication.this;
    }

    public static DinoApplication getApplication() {
        return mApplication;
    }

    public boolean isLogged() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.USER_ACCOUNT_PREFERENCE, MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.USER_TOKEN, "");

        return !token.isEmpty();
    }

    public void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.USER_ACCOUNT_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }

    public void setAccount(User user, boolean persist) {
        mUser = user;
        if (persist) saveAccountOnSharedPreferences(user);
    }

    public User getAccount() {
        return mUser;
    }

    public void saveAccountOnSharedPreferences(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.USER_ACCOUNT_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.USER_USERNAME, user.getUsername());
        editor.putString(Constants.USER_TOKEN, user.getToken().getAccessToken());
        if (user.getFirstName() != null && user.getLastName() != null) {
            editor.putString(Constants.USER_NAME, user.getFirstName() + user.getLastName());
        }

        editor.apply();
    }
}
