package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.client.WSClient;
import br.unicamp.ic.timeverde.dino.helper.Constants;
import br.unicamp.ic.timeverde.dino.model.Token;
import br.unicamp.ic.timeverde.dino.utils.StringUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe de Login
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_button)
    Button mButtonLogin;

    @BindView(R.id.login_user_email_input)
    EditText mInputLogin;

    @BindView(R.id.login_user_password_input)
    EditText mInputPassword;

    @OnClick(R.id.login_button)
    void doLogin(View view) {
        requestAuthentication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void requestAuthentication() {
        if (validateLoginInput()) {
            String email = mInputLogin.getText().toString();
            String password = mInputPassword.getText().toString();

            Call<Token> tokenCall = WSClient.getInstance().autenthicate(email, password);
            tokenCall.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {

                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateLoginInput() {
        String email = mInputLogin.getText().toString();
        String password = mInputPassword.getText().toString();

        return !email.isEmpty() &&
                StringUtils.isValidEmail(email) &&
                !password.isEmpty() && password.length() > 6;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
