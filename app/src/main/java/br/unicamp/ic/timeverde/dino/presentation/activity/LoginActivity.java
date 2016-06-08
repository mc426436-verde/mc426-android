package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.unicamp.ic.timeverde.dino.DinoApplication;
import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.client.WSClient;
import br.unicamp.ic.timeverde.dino.model.Token;
import br.unicamp.ic.timeverde.dino.model.User;
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

    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.login_button)
    Button mButtonLogin;

    @BindView(R.id.login_user_email_input)
    EditText mInputLogin;

    @BindView(R.id.login_user_password_input)
    EditText mInputPassword;

    private User mTemporaryUser;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void requestAuthentication() {
        if (validateLoginInput()) {
            mTemporaryUser = new User();
            mTemporaryUser.setUsername(mInputLogin.getText().toString());
            mTemporaryUser.setPassword(mInputPassword.getText().toString());

            Call<Token> tokenCall = WSClient.getInstance().autenthicate(mTemporaryUser.getUsername(), mTemporaryUser.getPassword());
            tokenCall.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response != null && response.body() != null) {
                        Log.d(TAG, "Authentication success!");
                        Token responseToken = response.body();
                        mTemporaryUser.setToken(responseToken);
                        validateUser();
                    } else {
                        stopProgress();
                        Toast.makeText(LoginActivity.this, "Login falhou!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    stopProgress();
                    Log.d(TAG, "Failure authenticating");
                    t.printStackTrace();
                }
            });
            startProgress();
        } else {
            Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }
    }

    private void validateUser() {
        StringBuilder authorization = new StringBuilder();
        authorization.append(mTemporaryUser.getToken().getTokenType());
        authorization.append(" ");
        authorization.append(mTemporaryUser.getToken().getAccessToken());
        Call<User> userCall = WSClient.getInstance().authorizeUser(authorization.toString());
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response != null && response.body() != null) {
                    stopProgress();
                    Log.d(TAG, "User logged successfully!");
                    User permanentUser = response.body();
                    permanentUser.setToken(mTemporaryUser.getToken());
                    DinoApplication.getApplication().setAccount(permanentUser, true);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    stopProgress();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                stopProgress();
                Log.d(TAG, "Failure requesting user");
                t.printStackTrace();
            }
        });
    }

    private void startProgress() {
        mProgress = new ProgressDialog(this);
        mProgress.setIndeterminate(true);
        mProgress.setMessage("Carregando informações");
        mProgress.setCancelable(false);
        mProgress.show();
    }

    private void stopProgress() {
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

    private boolean validateLoginInput() {
        String email = mInputLogin.getText().toString();
        String password = mInputPassword.getText().toString();

        return !email.isEmpty() &&
                !password.isEmpty();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.login_button)
    void doLogin(View view) {
        requestAuthentication();
    }
}
