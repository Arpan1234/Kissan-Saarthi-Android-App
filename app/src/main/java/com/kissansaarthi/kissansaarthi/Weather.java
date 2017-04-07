package com.kissansaarthi.kissansaarthi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

public class Weather extends Fragment {

    public static Weather newInstance(){
        Weather weather=new Weather();
        return weather;
    }
    public Weather(){}
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private View view;
    private FragmentActivity myContext;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view= inflater.inflate(R.layout.weather, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(myContext.getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
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
            // Show 3 total pages.  
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return ("Teste1").toUpperCase(l);
                case 1:
                    return ("Teste2").toUpperCase(l);

            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply 
     * displays dummy text. 
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this 
         * fragment. 
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

       /* @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.weather, container, false);

            return rootView;
        }*/
    }}