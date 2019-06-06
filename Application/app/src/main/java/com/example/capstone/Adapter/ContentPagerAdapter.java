package com.example.capstone.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.capstone.Fragment.TabDialogueFragment;
import com.example.capstone.Fragment.TabSummaryFragment;

public class ContentPagerAdapter extends FragmentStatePagerAdapter {
    private int mPageCount;
    public int roomNum;
    public int division;
    public int setValue;

    public ContentPagerAdapter(FragmentManager fm, int pageCount, int num, int div, int value) {
        super(fm);
        this.mPageCount = pageCount;
        this.roomNum = num;
        this.division = div;
        this.setValue = value;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabDialogueFragment homeFragment = new TabDialogueFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("num", roomNum);
                homeFragment.setArguments(bundle);
                return homeFragment;

            case 1:
                TabSummaryFragment tab2Fragment = new TabSummaryFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("RoomNum", roomNum);
                return tab2Fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}
