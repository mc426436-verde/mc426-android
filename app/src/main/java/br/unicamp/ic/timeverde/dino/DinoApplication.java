package br.unicamp.ic.timeverde.dino;

import android.app.Application;
import android.content.SharedPreferences;

import br.unicamp.ic.timeverde.dino.helper.Constants;
import br.unicamp.ic.timeverde.dino.model.Token;
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
        user.setAdmin(user.getAuthorities().contains(User.ROLE_ADMIN));
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
        editor.putString(Constants.USER_TOKEN_TYPE, user.getToken().getTokenType());
        editor.putBoolean(Constants.USER_IS_ADMIN, user.getAdmin());
        if (user.getFirstName() != null && user.getLastName() != null) {
            editor.putString(Constants.USER_NAME, user.getFirstName() + user.getLastName());
        }
        editor.apply();
    }

    public void setAccountFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.USER_ACCOUNT_PREFERENCE, MODE_PRIVATE);
        User user = new User();
        Token token = new Token();
        user.setUsername(sharedPreferences.getString(Constants.USER_USERNAME, ""));
        user.setFirstName(sharedPreferences.getString(Constants.USER_NAME, ""));
        token.setAccessToken(sharedPreferences.getString(Constants.USER_TOKEN, ""));
        token.setTokenType(sharedPreferences.getString(Constants.USER_TOKEN_TYPE, ""));
        user.setToken(token);
        user.setAdmin(sharedPreferences.getBoolean(Constants.USER_IS_ADMIN,false));
        mUser = user;
    }
}
