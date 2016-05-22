package br.unicamp.ic.timeverde.dino.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.MainPagerAdapter;
import br.unicamp.ic.timeverde.dino.helper.Constants;
import br.unicamp.ic.timeverde.dino.presentation.fragment.AccountFragment;
import br.unicamp.ic.timeverde.dino.presentation.fragment.AccountsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActiivty para gerenciar navaegação entre fragments
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_pager)
    ViewPager mViewPager;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_nav_drawer)
    NavigationView mNavigationView;

    @BindView(R.id.main_fragment_container)
    FrameLayout mFrameLayout;

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
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

        // Seta as tabs
        mTabLayout.setupWithViewPager(mViewPager);

        // Seta listener do NavDrawer
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

        mNavigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
    private void doLogout() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().remove(Constants.USER_TOKEN_PREFERENCE).commit();
        startActivity(new Intent(this, LoginActivity.class));
    }


    public void selectDrawerItem(MenuItem menuItem) {

        if (menuItem.isChecked()) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        Fragment fragment = null;

        // Mostra o fragment correto,  criando novo quando necessário
        switch (menuItem.getItemId()) {
            case R.id.nav_drawer_home_item:
                setTitle(getString(R.string.app_name));
                mViewPager.setCurrentItem(0);
                mViewPager.setVisibility(View.VISIBLE);
                mTabLayout.setVisibility(View.VISIBLE);
                mFrameLayout.setVisibility(View.GONE);
                break;
            case R.id.nav_drawer_account_item:
                setTitle(menuItem.getTitle());
                mViewPager.setVisibility(View.GONE);
                mTabLayout.setVisibility(View.GONE);
                fragment = new AccountFragment();
                mFrameLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_drawer_accounts_item:
                setTitle(menuItem.getTitle());
                mViewPager.setVisibility(View.GONE);
                mTabLayout.setVisibility(View.GONE);
                fragment = new AccountsFragment();
                mFrameLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_drawer_logout_item:
                doLogout();
                break;
        }

        // Muda para o fragment se necessário
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
        }

        // Marca o item como selecionado no NavDrawer
        mNavigationView.setCheckedItem(menuItem.getItemId());

        // Fecha o drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

}
