package se.www.malmo2night;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class UtestalleDetaljerActivity extends FragmentActivity implements ActionBar.TabListener {
    private static String utestalleNamn;
    private static String utestalleLat;
    private static String utestalleLng;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static String[] utestalleDetaljer;
    private Context context = this;
    private boolean finishedDownload = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utestalle_detaljer);
        utestalleNamn = getIntent().getStringExtra("UtestalleNamn");
        System.out.println("3");
        setTitle(utestalleNamn);
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        System.out.println("4");
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        System.out.println("5");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment information = new UtestalleDetaljer_Information();
                    return information;
                case 1:
                    Fragment kommentarer = new UtestalleDetaljer_Kommentarer();
                    return kommentarer;
            }
            return null;
        }

        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_utestalle_detaljer_information).toUpperCase();
                case 1:
                    return getString(R.string.title_utestalle_detaljer_kommentarer).toUpperCase();
            }
            return null;
        }
    }

    public static String getUtestalleNamn() {
        return utestalleNamn;
    }

}
