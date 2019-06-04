package com.example.capstone.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.capstone.Adapter.ContentPagerAdapter;
import com.example.capstone.PreferenceUtil;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

//회의종료 화면
public class CloseActivity extends AppCompatActivity {
    private Context mContext;
    private TabLayout mTabLayout;

    private ViewPager mViewPager;
    private ContentPagerAdapter mContentPagerAdapter;

    public int roomNum;
    public String division;
    public int setValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);


        Intent intent = getIntent();

        roomNum = intent.getExtras().getInt("RoomNum");
        division = PreferenceUtil.getInstance(this).getStringExtra("Division");
      //  Log.d("debug", division);
        setValue = PreferenceUtil.getInstance(this).getIntExtra("SettingValue");
    //    Log.d("debug", setValue+"");
        if (intent.getExtras().getBoolean("status")) {
            //회의후 넘어온 것이라면 회의 액티비티 종료
            //이전 액티비티 종료
            ConferenceActivity conferenceActivity = (ConferenceActivity) ConferenceActivity.activity;
            conferenceActivity.finish();
        }

        //탭
        mContext = getApplicationContext();
        mTabLayout = (TabLayout) findViewById(R.id.tab);

        mTabLayout.addTab(mTabLayout.newTab().setText("Dialogue"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Summary"));

        mViewPager = (ViewPager) findViewById(R.id.pager_content);

        mContentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), roomNum/*, division, setValue*/);

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
        Intent intent = new Intent(CloseActivity.this, ChannelListActivity.channelListActivity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //        super.onBackPressed();
    }
}
