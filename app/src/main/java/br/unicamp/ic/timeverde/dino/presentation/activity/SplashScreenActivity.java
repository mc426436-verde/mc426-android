package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

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

        //TODO fazer requisição verrificando se o token é válido
        if (PreferenceManager.getDefaultSharedPreferences(this).contains(Constants.USER_TOKEN_PREFERENCE)){
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
