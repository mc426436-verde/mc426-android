package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.presentation.fragment.DeviceFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceRoomActivity extends AppCompatActivity {

    @BindView(R.id.device_room_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.device_room_fragment_container)
    FrameLayout deviceRoomFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_room);
        ButterKnife.bind(this);

        // Seta a toolbar
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.device_room_fragment_container, new DeviceFragment());
        fragmentTransaction.commit();
    }

}
