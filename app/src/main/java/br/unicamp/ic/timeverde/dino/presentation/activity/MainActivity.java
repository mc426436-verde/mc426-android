package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.MainPagerAdapter;
import br.unicamp.ic.timeverde.dino.helper.Constants;
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

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Caso não tenha o token vai para a tela de login
        if (!PreferenceManager.getDefaultSharedPreferences(this).contains(Constants.USER_TOKEN_PREFERENCE)) {
            doLogout();
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //Inicia o Drawer no clique no botão
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    // Inicia Activity de Login
    private void doLogout(){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
