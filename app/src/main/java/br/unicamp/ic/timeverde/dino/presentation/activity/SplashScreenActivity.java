package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

        if (DinoApplication.getApplication().isLogged()){
            DinoApplication.getApplication().setAccountFromSharedPreferences();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
