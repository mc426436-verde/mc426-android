package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import br.unicamp.ic.timeverde.dino.DinoApplication;
import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.helper.Constants;

/**
 * Classe SplashScreen para verificar qual tela o app deve iniciar
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Bundle bundle = new Bundle();

        if (DinoApplication.getApplication().isLogged()) {
            DinoApplication.getApplication().setAccountFromSharedPreferences();
            handleNextScreen(bundle, MainActivity.class);
        } else {
            handleNextScreen(bundle, LoginActivity.class);
        }
    }

    private void handleNextScreen(@NonNull Bundle extras, Class tClass) {
        Uri uri = getIntent().getData();
        if (uri != null && uri.getHost().equals(Constants.TOGGLE_DEVICE)) {
            Long deviceId = Long.valueOf(uri.getLastPathSegment());
            extras.putLong(MainActivity.EXTRA_TOGGLE_MACRO_ID, deviceId);
        }
        Intent intent = new Intent(this, tClass);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
