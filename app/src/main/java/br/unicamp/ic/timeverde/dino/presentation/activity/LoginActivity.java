package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.unicamp.ic.timeverde.dino.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

}
