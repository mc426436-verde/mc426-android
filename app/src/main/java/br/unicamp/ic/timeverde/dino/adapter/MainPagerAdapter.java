package br.unicamp.ic.timeverde.dino.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.ic.timeverde.dino.presentation.fragment.DeviceFragment;
import br.unicamp.ic.timeverde.dino.presentation.fragment.MacroFragment;
import br.unicamp.ic.timeverde.dino.presentation.fragment.RoomFragment;

/**
 * Adapter para MainPager
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    //Constante com o total de tabs
    final static int NUMBER_OF_TABS = 3;

    //Constante com o nome das tabs
    final static ArrayList<String> TAB_NAMES = new ArrayList<>(Arrays.asList("COMODÔS","DEVICES","MACROS"));

    //Fragment de comodôs
    final RoomFragment roomFragment;

    //Fragment de itens
    final DeviceFragment deviceFragment;

    //Fragment de macros
    final MacroFragment macroFragment;


    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        roomFragment = new RoomFragment();
        deviceFragment = new DeviceFragment();
        macroFragment = new MacroFragment();
    }

    @Override
    public Fragment getItem(int tab) {
        switch (tab) {
            case 0:
                return this.roomFragment;
            case 1:
                return this.deviceFragment;
            default:
                return this.macroFragment;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_NAMES.get(position);
    }

}