
package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.presentation.fragment.DeviceFragment;
import br.unicamp.ic.timeverde.dino.presentation.fragment.NewMacroFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMacroActivity extends AppCompatActivity {

    @BindView(R.id.macro_room_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_macro);
        ButterKnife.bind(this);

        // Seta a toolbar
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.new_macro_fragment_container, new NewMacroFragment());
        fragmentTransaction.commit();
    }

}
