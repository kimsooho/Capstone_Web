package com.example.capstone.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.capstone.Adapter.ContentPagerAdapter;
import com.example.capstone.R;

//회의종료 화면
public class CloseActivity extends AppCompatActivity {
    private Context mContext;
    private TabLayout mTabLayout;

    private ViewPager mViewPager;
    private ContentPagerAdapter mContentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        //이전 액티비티 종료
        ConferenceActivity conferenceActivity = (ConferenceActivity)ConferenceActivity.activity;
        conferenceActivity.finish();

        //탭
        mContext = getApplicationContext();
        mTabLayout = (TabLayout) findViewById(R.id.tab);

        mTabLayout.addTab(mTabLayout.newTab().setText("Dialouge"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Summary"));

        mViewPager = (ViewPager) findViewById(R.id.pager_content);

        mContentPagerAdapter = new ContentPagerAdapter(

                getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(mContentPagerAdapter);
        mViewPager.addOnPageChangeListener(

                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override

            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CloseActivity.this, ChannelListActivity.class);
        startActivity(intent);
        //        super.onBackPressed();
    }
    public void home(View v)//btn_home
    {
        Intent intent = new Intent(CloseActivity.this, ChannelListActivity.class);
        startActivity(intent);
    }
}
