package com.example.dipto.movietune.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.dipto.movietune.R;
import com.example.dipto.movietune.fragment.NewRelease;
import com.example.dipto.movietune.fragment.TestPagination;
import com.example.dipto.movietune.fragment.TopRated;
import com.example.dipto.movietune.fragment.Upcoming;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dipto on 8/22/2017.
 */

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar ;
    ViewPager viewPager ;
    TabLayout tabLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //showCustomToolbar();

        viewPager = (ViewPager) findViewById(R.id.viewpager) ;
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager()) ;
        viewPagerAdapter.addFragment(new NewRelease(), "New Release");
        viewPagerAdapter.addFragment(new TopRated(), "Top Rated");
        viewPagerAdapter.addFragment(new Upcoming(), "Upcoming");
        viewPager.setAdapter(viewPagerAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>() ;
        private final List<String> fragmentTitle = new ArrayList<>() ;

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position) ;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment) ;
            fragmentTitle.add(title) ;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position) ;
        }
    }

    private void showCustomToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
