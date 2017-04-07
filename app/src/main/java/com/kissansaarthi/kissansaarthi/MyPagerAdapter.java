package com.kissansaarthi.kissansaarthi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hp on 15-Mar-16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private String tt[]=new String[]{"frag1","frag2"};
    Weather context;

    public MyPagerAdapter(FragmentManager fm, Weather context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new Weather1();
        }
        else if(position == 1){
            return new Weather2();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tt[position];
    }
}
