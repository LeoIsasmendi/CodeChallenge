package android.example.com.codechallenge.ui;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.example.com.codechallenge.R;
import android.util.Log;

public class DetailsActivity extends AppCompatActivity {

    private static final int MAX_PAGES = 2;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        if (savedInstanceState == null) {
            mBundle = getIntent().getExtras();
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;
            Bundle bundle;
            switch (position) {
                case 0:
                    fragment = Fragment.instantiate(getApplication(), DetailFragment.class.getName());
                    bundle = new Bundle();
                    bundle.putString("PET_NAME", mBundle.getString("PET_NAME"));
                    bundle.putString("PET_CATEGORY", mBundle.getString("PET_CATEGORY"));
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = Fragment.instantiate(getApplication(), PhotoFragment.class.getName());
                    bundle = new Bundle();
                    bundle.putString("PHOTO_URL", mBundle.getString("PHOTO_URL"));
                    fragment.setArguments(bundle);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return MAX_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab_title_1);
                case 1:
                    return getResources().getString(R.string.tab_title_2);
            }
            return null;
        }
    }
}
