package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.MainPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActiivty para gerenciar navaegação entre fragments
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_pager)
    ViewPager mViewPagerMain;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_tabs)
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seta o view e faz o bind
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Seta a toolbar
        setSupportActionBar(mToolbar);

        // Seta o adapter do ViewPager
        mViewPagerMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

        // Seta as tabs
        mTabLayout.setupWithViewPager(mViewPagerMain);
    }

}
