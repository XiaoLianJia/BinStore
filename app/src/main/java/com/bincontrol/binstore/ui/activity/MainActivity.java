package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.bincontrol.binstore.ui.BottomNavigationViewHelper;
import com.bincontrol.binstore.R;
import com.bincontrol.binstore.ui.adapter.ViewPagerAdapter;
import com.bincontrol.binstore.ui.fragment.AgencyFragment;
import com.bincontrol.binstore.ui.fragment.HomeFragment;
import com.bincontrol.binstore.ui.fragment.MineFragment;
import com.bincontrol.binstore.ui.fragment.SearchFragment;

public class MainActivity extends BaseActivity {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private MenuItem mMenuItem;

    private static final int FRAGEMENT_HOME = 0;
    private static final int FRAGEMENT_2 = 1;
    private static final int FRAGEMENT_3 = 2;
    private static final int FRAGEMENT_MINE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomNavigationView();
        initViewPager();
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNavigationView() {

        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(FRAGEMENT_HOME);
                        return true;
                    case R.id.navigation_search:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_agency:
                        mViewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigation_mine:
                        mViewPager.setCurrentItem(FRAGEMENT_MINE);
                        return true;
                }
                return false;
            }
        });

        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
    }

    /**
     * 初始化页面
     */
    private void initViewPager() {
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mBottomNavigationView.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new HomeFragment());
        viewPagerAdapter.addFragment(new SearchFragment());
        viewPagerAdapter.addFragment(new AgencyFragment());
        viewPagerAdapter.addFragment(new MineFragment());
        mViewPager.setAdapter(viewPagerAdapter);
    }

}