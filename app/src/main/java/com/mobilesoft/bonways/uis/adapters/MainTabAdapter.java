package com.mobilesoft.bonways.uis.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobilesoft.bonways.uis.fragments.FavoritesFragment;
import com.mobilesoft.bonways.uis.fragments.MainFragment;
import com.mobilesoft.bonways.uis.fragments.MapFragment;

/**
 * Created by Lyonnel Dzotang on 24/01/2017.
 */

public class MainTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MainTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MapFragment tab1 = new MapFragment();
                return tab1;
            case 1:
                MainFragment tab2 = new MainFragment();
                return tab2;
            case 2:
                FavoritesFragment tab3 = new FavoritesFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
