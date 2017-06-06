package com.hb.happnissbilldemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hb.happnissbilldemo.R;
import com.hb.happnissbilldemo.fragment.FamilyFragment;
import com.hb.happnissbilldemo.fragment.RecordFragment;
import com.hb.happnissbilldemo.fragment.UserFragment;


public class MainActivity extends AppCompatActivity {

    private int mCurrentFragment;
    RecordFragment mRecordFragment;
    UserFragment mUserFragment;
    FamilyFragment mFamilyFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (mCurrentFragment == item.getItemId()) return true;
            FragmentTransaction ft;
            switch (item.getItemId()) {
                case R.id.navigation_records:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.content, mRecordFragment);
                    ft.commit();
                    mCurrentFragment = item.getItemId();
                    return true;
                case R.id.navigation_user:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.content, mUserFragment);
                    ft.commit();
                    mCurrentFragment = item.getItemId();
                    return true;
                case R.id.navigation_family:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.content, mFamilyFragment);
                    ft.commit();
                    mCurrentFragment = item.getItemId();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentFragment = R.id.navigation_records;

        mRecordFragment = RecordFragment.newInstance();
        mUserFragment = UserFragment.newInstance("A", "B");
        mFamilyFragment = FamilyFragment.newInstance("1", "2");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, mRecordFragment);
        ft.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
