package com.example.atakanyenel.myapplication;

import android.app.usage.UsageEvents;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by berkedifyeli on 08/05/2017.
 */

public class PageAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public PageAdapter(FragmentManager fm, int numTabs){
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                Tasks frg1 = new Tasks();
                return frg1;
            case 1:
                Events frg2 = new Events();
                return frg2;
            case 2:
                Equipments frg3 = new Equipments();
                return frg3;
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return this.numTabs;
    }
}
