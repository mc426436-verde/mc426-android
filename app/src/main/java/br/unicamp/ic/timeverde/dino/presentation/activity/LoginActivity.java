package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.unicamp.ic.timeverde.dino.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_button)
    Button mBtLogin;

    @BindView(R.id.login_user_email_input)
    EditText mEtLoginInput;

    @BindView(R.id.login_user_password_input)
    EditText mEtPasswordInput;

    @OnClick(R.id.login_button)
    public void doLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

}
